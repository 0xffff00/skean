package party.threebody.skean.dict.service;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.SetUtils;
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
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
public class SearchEngine {

    @Autowired WordDao wordDao;
    @Autowired CriteriaBuilder criteriaBuilder;

    @Autowired WordService wordService;
    @Autowired BasicRelationDao basicRelationDao;
    @Autowired X1RelationDao x1RelationDao;

    public Set<String> search(Map<String, Object> paramMap) {
        Map<String, Object> basicParamMap = Maps.filterKeys(paramMap, key -> !isKindOfRelCriterion(key));
        Map<String, Object> relParamMap = Maps.filterKeys(paramMap, key -> !isKindOfRelCriterion(key));
        Criteria basicCriteria = criteriaBuilder.toCriteria(basicParamMap);
        // TODO build RelCriterion[] from relParamMap
        List<RelCriterion> relCriteria = null;
        return search(basicCriteria, relCriteria);
    }

    public static boolean isKindOfRelCriterion(String paramName) {
        return Stream.of(RelPredicate.values()).map(RelPredicate::toString).anyMatch(paramName::equals)
                || paramName.startsWith(RelMapper.attr.toString() + "^")
                || paramName.startsWith(RelMapper.rel.toString() + "^");
    }

    /**
     * 简单的优化：
     * 如果sql结果较小，正向过滤，SQL result->DAG result
     * 否则反向过滤,DAG result->SQL result
     *
     * @param basicCriteria 基础查询条件，可翻译成sql的where子句，代价小
     * @param relCriteria   高级关系查询条件，需要用到DAG的递归搜索，代价较高
     * @return
     */
    public Set<String> search(Criteria basicCriteria, List<RelCriterion> relCriteria) {
        int cnt1 = wordDao.countAllWordsMentioned(basicCriteria);
        List<String> words = wordDao.listAllWordsMentioned(basicCriteria);
        return search0(new HashSet<>(words), new LinkedList<>(relCriteria));
    }

    public Set<String> search0(Set<String> unfiltered, LinkedList<RelCriterion> relCriteria) {
        if (relCriteria.isEmpty()) {
            return unfiltered;
        }
        RelCriterion crit = relCriteria.pollFirst();
        if (unfiltered.size() > 30) {
            // forward search in DAG, then intersect previous result
            Set<String> res = fetchByRelCriterion(crit);
            Set<String> filtered = SetUtils.intersection(unfiltered, res);
            return search0(filtered, relCriteria);
        } else {
            // backward search in DAG from each node in previous result
            Set<String> filtered = unfiltered.stream().filter(word -> {
                Set<String> wordsMapped = relMap(word, crit.getMapper(), crit.getMapperArg());
                return wordsMapped.stream().anyMatch(w2 ->
                        existsPathOnDAG(w2, crit.getPredicate(), crit.getPredicateArg()));
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
    private boolean existsPathOnDAG(String src, RelPredicate pred, String dst) {
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
