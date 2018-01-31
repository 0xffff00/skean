package party.threebody.skean.dict.service;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.misc.SkeanNotImplementedException;
import party.threebody.skean.web.data.CriteriaBuilder;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static party.threebody.skean.dict.service.RelPredicate.*;

@Service
public class SearchEngine {

    @Autowired WordDao wordDao;
    @Autowired CriteriaBuilder criteriaBuilder;

    @Autowired WordService wordService;
    @Autowired BasicRelationDao basicRelationDao;
    @Autowired X1RelationDao x1RelationDao;

    public static final int MIN_FORWARD_SEARCH = 10;

    public Collection<String> search(Map<String, Object> paramMap) {
        Map<String, Object> basicParamMap = Maps.filterKeys(paramMap, key -> !isKindOfRelCriterion(key));
        Map<String, Object> relParamMap = Maps.filterKeys(paramMap, key -> !isKindOfRelCriterion(key));
        Criteria basicCriteria = criteriaBuilder.toCriteria(basicParamMap);
        List<RelCriterion> relCriteria = relParamMap.entrySet().stream()
                .map(entry -> RelCriterion.of(entry.getKey(), entry.getValue().toString()))
                .collect(toList());
        return search(basicCriteria, relCriteria);
    }


    /**
     * <h3>调优2：</h3>
     * 调整relCriteria的顺序，优先级如下：
     * <ol>
     * <li>super*Of</li>
     * <li>sub*Of</li>
     * <li>subOf instanceOf</li>
     * </ol>
     * <h3>调优3：</h3>
     * 如果basicCriteria为空，直接忽略
     *
     * @param basicCriteria 基础查询条件，可翻译成sql的where子句，代价小，可带limit参数保证性能
     * @param relCriteria   高级关系查询条件，需要用到DAG的递归搜索，代价较高
     * @return
     */
    public Collection<String> search(Criteria basicCriteria, List<RelCriterion> relCriteria) {
        List<String> res0;
        if (basicCriteria == null || basicCriteria.isEmpty()) {
            res0 = null;  // null means full
        } else {
            res0 = wordDao.listAllWordsMentioned(basicCriteria);
        }
        return search0(res0, new LinkedList<>(relCriteria));
    }

    private LinkedList<RelCriterion> rearrangeRelCriteria(List<RelCriterion> relCriteria) {
        List<RelCriterion> res0 = Stream.of(
                relCriteria.stream().filter(c -> hasPredicateAmong(c, superOf, supersetOf, supertopicOf, definitionOf)),
                relCriteria.stream().filter(c -> hasPredicateAmong(c, subsetOf, subtopicOf)),
                relCriteria.stream().filter(c -> hasPredicateAmong(c, subOf, instanceOf))
        ).flatMap(Function.identity()).collect(toList());
        return new LinkedList<>(res0);
    }

    private static boolean hasPredicateAmong(RelCriterion relCriterion, RelPredicate... predicates) {
        RelPredicate pred = relCriterion.getPredicate();
        for (RelPredicate p : predicates) {
            if (p.equals(pred)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isKindOfRelCriterion(String paramName) {
        return Stream.of(RelPredicate.values()).map(RelPredicate::toString).anyMatch(paramName::equals)
                || paramName.startsWith(RelMapper.attr.toString() + "^")
                || paramName.startsWith(RelMapper.rel.toString() + "^");
    }

    /**
     * if  previous result is too large,
     * forward search in DAG and intersect previous result,
     * else backward search in DAG from each node in previous result
     *
     * @param unfiltered  previous result,if null,means full set
     * @param relCriteria
     * @return a set filtered by all criteria
     */
    private Collection<String> search0(Collection<String> unfiltered, LinkedList<RelCriterion> relCriteria) {
        if (relCriteria.isEmpty()) {
            return unfiltered;
        }
        RelCriterion crit = relCriteria.pollFirst();
        if (unfiltered == null) {
            Set<String> filtered = fetchByRelCriterion(crit);
            return search0(filtered, relCriteria);
        }
        if (unfiltered.size() > MIN_FORWARD_SEARCH) {
            // forward search in DAG, then intersect previous result
            Set<String> res = fetchByRelCriterion(crit);
            Collection<String> filtered = CollectionUtils.intersection(unfiltered, res);
            return search0(filtered, relCriteria);
        } else {
            // backward search in DAG from each node in previous result
            Set<String> filtered = unfiltered.stream().filter(word -> {
                Set<String> wordsMapped = relMap(word, crit.getMapper(), crit.getMapperArg());
                return wordsMapped.stream().anyMatch(w2 ->
                        existsPathInDAG(w2, crit.getPredicate(), crit.getPredicateArg()));
            }).collect(toSet());
            return search0(filtered, relCriteria);
        }
    }

    private Set<String> fetchByRelCriterion(RelCriterion relCriterion) {
        RelMapper relMapper = relCriterion.getMapper();
        String attrName = relCriterion.getMapperArg();
        String object = relCriterion.getPredicateArg();
        RelPredicate pred = relCriterion.getPredicate();
        Set<String> words = fetchByObject(pred, object);
        switch (relMapper) {
            case attr:
                return words.stream().flatMap(word ->
                        wordService.listX1RelsBySAD(null, attrName, word).stream().map(X1Relation::getSrc)
                ).collect(toSet());
            case rel:
                return words.stream().flatMap(word ->
                        wordService.listX1RelsBySAD(word, attrName, null).stream().map(X1Relation::getDst)
                ).collect(toSet());
            case self:
            default:
                return words;
        }
    }

    private Set<String> fetchByObject(RelPredicate pred, String object) {
        switch (pred) {
            case subOf:
                return wordService.fetchSubESR(object);
            case subsetOf:
                return wordService.fetchSubsetESR(object);
            case subtopicOf:
                return wordService.fetchSubtopicESR(object);
            case instanceOf:
                return wordService.fetchDefinitionESA(object);
            case superOf:
                return wordService.fetchSuperESR(object);
            case supersetOf:
                return wordService.fetchSupersetESR(object);
            case supertopicOf:
                return wordService.fetchSupertopicESR(object);
            default:
                throw new SkeanNotImplementedException();
        }
    }

    /**
     * relMap('Tom',attr,'age')==[24]
     * relMap('Tom',self,null)=='Tom'
     * relMap('Tom',rel,'son')==['MomOfTom','DadOfTom']
     */
    private Set<String> relMap(String subject, RelMapper relMapper, String attrName) {
        switch (relMapper) {
            case attr:
                return wordService.listX1RelsBySAD(subject, attrName, null).stream()
                        .map(X1Relation::getDst).collect(toSet());
            case rel:
                return wordService.listX1RelsBySAD(null, attrName, subject).stream()
                        .map(X1Relation::getSrc).collect(toSet());
            case self:
            default:
                return Collections.singleton(subject);
        }
    }

    /**
     * judge whether a connected path exists on relation DAG. 'super' direction is prefered.
     *
     * @param src not null
     * @param dst not null
     */
    private boolean existsPathInDAG(String src, RelPredicate pred, String dst) {
        if (src == null || dst == null) {
            return false;
        }
        switch (pred) {
            case subOf:
                return wordService.fetchSuperESR(src).contains(dst);
            case subsetOf:
                return wordService.fetchSupersetESR(src).contains(dst);
            case subtopicOf:
                return wordService.fetchSupertopicRSR(src).contains(dst);
            case instanceOf:
                return wordService.fetchDefinitionESA(src).contains(dst);
            case superOf:
                return wordService.fetchSuperESR(dst).contains(src);
            case supersetOf:
                return wordService.fetchSupersetESR(dst).contains(src);
            case supertopicOf:
                return wordService.fetchSupertopicRSR(dst).contains(src);
            default:
                throw new SkeanNotImplementedException();
        }

    }


}
