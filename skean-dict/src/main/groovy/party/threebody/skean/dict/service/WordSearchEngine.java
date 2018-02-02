package party.threebody.skean.dict.service;

import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.dict.domain.criteria.*;
import party.threebody.skean.misc.SkeanNotImplementedException;
import party.threebody.skean.web.data.CriteriaBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class WordSearchEngine {

    @Autowired WordDao wordDao;
    @Autowired CriteriaBuilder criteriaBuilder;

    @Autowired WordService wordService;
    @Autowired BasicRelationDao basicRelationDao;
    @Autowired X1RelationDao x1RelationDao;

    public static final int LARGE_SIZE_THRESHOLD = 20;

    public Set<String> searchByJson(String json) {
        return search(CritTrees.fromJson(json));
    }

    /**
     * prior:
     * BasicFilterNode[] > RelFilterNode > MapNode
     *
     * @param critTreeNodes
     * @return
     */
    public Set<String> search(List<CritTreeNode> critTreeNodes) {
        return filter(null, critTreeNodes);
    }

    private static boolean tooManyUnfiltered(Set<String> unfiltered) {
        return unfiltered == null || unfiltered.size() > LARGE_SIZE_THRESHOLD;
    }

    /**
     * unfiltered --[filter]--> filtered
     * <p>
     * if unfiltered are too many, filtered = sql results by crits INTERSECTION filtered
     * if unfiltered are not many, filtered = sql results by crits and IN-unfiltered-crit
     *
     * @param unfiltered      unfiltered set
     * @param textFilterNodes can be tranlated to crits
     * @return filtered set
     */
    private Set<String> filterBySql(Set<String> unfiltered, List<TextFilterNode> textFilterNodes) {
        List<BasicCriterion> crits = textFilterNodes.stream().map(TextFilterNode::toBasicCriterion).collect(toList());
        if (unfiltered == null) {
            List<String> sqlResults = wordDao.listAllWordsMentioned(Criteria.of(crits));
            return new HashSet<>(sqlResults);
        } else if (unfiltered.isEmpty()) {
            return unfiltered;
        } else if (tooManyUnfiltered(unfiltered)) {
            List<String> sqlResults = wordDao.listAllWordsMentioned(Criteria.of(crits));
            return sqlResults.stream().filter(unfiltered::contains).collect(toSet());
        } else {
            BasicCriterion crit1 = new BasicCriterion("text", "IN", unfiltered);
            crits.add(crit1);
            return new HashSet<>(wordDao.listAllWordsMentioned(Criteria.of(crits)));
        }
    }

    private Set<String> filter(Set<String> unfiltered, RelFilterNode relFilterNode) {
        if (unfiltered == null) {
            return filterFromFullSet(relFilterNode);
        } else if (tooManyUnfiltered(unfiltered)) {
            return SetUtils.intersection(filterFromFullSet(relFilterNode), unfiltered);
        } else {
            return unfiltered.stream().filter(src -> evaluate(src, relFilterNode)).collect(toSet());
        }
    }

    private Set<String> filter(Set<String> unfiltered, MapNode mapNode) {
        if (unfiltered == null) {
            // TODO to be removed cuz of low performance.
            unfiltered = new HashSet<>(wordDao.listAllWordsMentioned(Criteria.ALLOW_ALL));
        }
        // TODO realize backward derivation (filter->unmap->..->unmap)

        return unfiltered.stream().filter(x -> {
            Set<String> mapped = mapping(x, mapNode).collect(toSet());
            Set<String> res1 = filter(mapped, mapNode.getChildren());
            return !res1.isEmpty();
        }).collect(toSet());

    }

    private Set<String> filter(Set<String> unfiltered, List<CritTreeNode> nodes) {
        if (nodes == null) {
            return unfiltered;
        }
        // divide nodes to 3 parts
        List<TextFilterNode> textFilterNodes = nodes.stream()
                .filter(TextFilterNode.class::isInstance)
                .map(TextFilterNode.class::cast)
                .collect(toList());
        List<RelFilterNode> relFilterNodes = nodes.stream()
                .filter(RelFilterNode.class::isInstance)
                .map(RelFilterNode.class::cast)
                .collect(toList());
        List<MapNode> mapNodes = nodes.stream()
                .filter(MapNode.class::isInstance)
                .map(MapNode.class::cast)
                .collect(toList());

        Set<String> res = null;
        res = filterBySql(unfiltered, textFilterNodes);
        for (RelFilterNode node : relFilterNodes) {
            res = filter(res, node);
        }
        for (MapNode node : mapNodes) {
            res = filter(res, node);
        }
        return res;
    }

    private boolean evaluate(String src, RelFilterNode relFilterNode) {
        String dst = relFilterNode.getValStr();
        switch (relFilterNode.getPred()) {
            case subOf:
                return wordService.fetchSuperESR(src).contains(dst);
            case subsOf:
                return wordService.fetchSuperESR(src).contains(dst);
            case subtOf:
                return wordService.fetchSupertopicESR(src).contains(dst);
            case instOf:
                return wordService.fetchDefinitionESA(src).contains(dst);
            case supOf:
                return wordService.fetchSubESR(src).contains(dst);
            case supsOf:
                return wordService.fetchSubsetESR(src).contains(dst);
            case suptOf:
                return wordService.fetchSubtopicESR(src).contains(dst);
            case defOf:
                return wordService.fetchInstanceESA(src).contains(dst);
            default:
                throw new SkeanNotImplementedException();
        }
    }

    private Stream<String> mapping(String unmapped, MapNode mapNode) {
        String arg = mapNode.getX();
        switch (mapNode.getMapper()) {
            case attr:
                return wordService.listX1RelsBySAD(unmapped, arg, null).stream()
                        .map(X1Relation::getDst);
            case ref:
                return wordService.listX1RelsBySAD(null, arg, unmapped).stream()
                        .map(X1Relation::getSrc);
            case sub:
                return wordService.fetchSubESR(unmapped).stream();
            case subs:
                return wordService.fetchSubsetESR(unmapped).stream();
            case subt:
                return wordService.fetchSubtopicESR(unmapped).stream();
            case inst:
                return wordService.fetchInstanceESA(unmapped).stream();
            case sup:
                return wordService.fetchSuperESR(unmapped).stream();
            case sups:
                return wordService.fetchSupersetESR(unmapped).stream();
            case supt:
                return wordService.fetchSupertopicESR(unmapped).stream();
            case def:
                return wordService.fetchDefinitionESA(unmapped).stream();
            default:
                throw new SkeanNotImplementedException();

        }
    }

    private Set<String> filterFromFullSet(RelFilterNode relFilterNode) {
        String what = relFilterNode.getValStr();
        switch (relFilterNode.getPred()) {
            case subOf:
                return wordService.fetchSubESR(what);
            case subsOf:
                return wordService.fetchSubsetESR(what);
            case subtOf:
                return wordService.fetchSubtopicESR(what);
            case instOf:
                return wordService.fetchInstanceESA(what);
            case supOf:
                return wordService.fetchSuperESR(what);
            case supsOf:
                return wordService.fetchSupersetESR(what);
            case suptOf:
                return wordService.fetchSupertopicESR(what);
            case defOf:
                return wordService.fetchDefinitionESA(what);
            default:
                throw new SkeanNotImplementedException();
        }
    }


}
