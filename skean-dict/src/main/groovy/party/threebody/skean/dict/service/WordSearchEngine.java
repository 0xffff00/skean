package party.threebody.skean.dict.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.Criterion;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.dict.domain.criteria.FilterPhrase;
import party.threebody.skean.dict.domain.criteria.MapPhrase;
import party.threebody.skean.dict.domain.criteria.WordCriteriaPhrase;
import party.threebody.skean.misc.SkeanNotImplementedException;
import party.threebody.skean.web.data.CriteriaBuilder;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
public class WordSearchEngine {

    @Autowired WordDao wordDao;
    @Autowired CriteriaBuilder criteriaBuilder;

    @Autowired WordService wordService;
    @Autowired BasicRelationDao basicRelationDao;
    @Autowired X1RelationDao x1RelationDao;

    public static final int MIN_FORWARD_SEARCH = 10;

    public Collection<String> search(Map<String, Object> paramMap) {
        // TODO
        return null;
    }

    /**
     * backward derivation
     *
     * @param phrases must end with a FilterPhrase
     * @return
     */
    public Collection<String> search2(LinkedList<WordCriteriaPhrase> phrases) {
        Collection<String> result = null;
        while (!phrases.isEmpty()) {
            if (phrases.getLast() instanceof FilterPhrase) {
                WordCriteriaPhrase ph = phrases.getLast();
                List<FilterPhrase> filterPhrases = new ArrayList<>();
                for (; ph instanceof FilterPhrase; ph = phrases.getLast()) {
                    filterPhrases.add((FilterPhrase) phrases.pollLast());
                    if (phrases.isEmpty()) {
                        break;
                    }
                }
                result = unfilter(filterPhrases, result);
            } else { // MapPhrase
                MapPhrase mapPhrase = (MapPhrase) phrases.pollLast();
                result = unmap(mapPhrase, result);
            }
        }
        return result;
    }

    /**
     * umapped <--[unmap]-- mapped
     *
     * @param mapPhrase
     * @param mapped    mapped set
     * @return umapped set
     */
    private Collection<String> unmap(MapPhrase mapPhrase, Collection<String> mapped) {
        return mapped.stream().flatMap(x -> unmap(mapPhrase, x)).collect(toSet());
    }

    /**
     * umapped <--[unmap]-- mapped
     */
    private Stream<String> unmap(MapPhrase mapPhrase, String mapped) {
        String arg = mapPhrase.getArg();
        switch (mapPhrase.getType()) {
            case attr:
                return wordService.listX1RelsBySAD(null, arg, mapped).stream()
                        .map(X1Relation::getSrc);
            case ref:
                return wordService.listX1RelsBySAD(mapped, arg, null).stream()
                        .map(X1Relation::getDst);
            case subOf:
                return wordService.fetchSubESR(mapped).stream();
            case subsetOf:
                return wordService.fetchSubsetESR(mapped).stream();
            case subtopicOf:
                return wordService.fetchSubtopicESR(mapped).stream();
            case instanceOf:
                return wordService.fetchInstanceESA(mapped).stream();
            case superOf:
                return wordService.fetchSuperESR(mapped).stream();
            case supersetOf:
                return wordService.fetchSupersetESR(mapped).stream();
            case supertopicOf:
                return wordService.fetchSupertopicESR(mapped).stream();
            case definitionOf:
                return wordService.fetchDefinitionESA(mapped).stream();
            default:
                throw new SkeanNotImplementedException();
        }
    }

    /**
     * unfiltered <--[unfilter]-- filtered
     * <p>
     * if filtered too large, unfiltered = sql results on filterPhrases INTERSECTION filtered
     * if filtered not large, unfiltered = sql results on filterPhrases and IN-filtered-items-criterion
     *
     * @param filterPhrases
     * @param filtered      filtered set, if null means full set
     * @return unfiltered set
     */
    private Collection<String> unfilter(List<FilterPhrase> filterPhrases, Collection<String> filtered) {
        Criterion[] crits = filterPhrases.stream()
                .map(FilterPhrase::getBasicCriterion).toArray(BasicCriterion[]::new);
        if (filtered == null || filtered.size() > MIN_FORWARD_SEARCH) {
            // if filtered too large, unfiltered = sql results on filterPhrases INTERSECTION filtered
            List<String> sqlResults = wordDao.listAllWordsMentioned(Criteria.of(crits));
            if (filtered == null) {
                return sqlResults;
            } else {
                return CollectionUtils.intersection(sqlResults, filtered);
            }
        } else {
            // if filtered not large, unfiltered = sql results on filterPhrases and IN-filtered-items-criterion
            BasicCriterion inFilteredCriterion = new BasicCriterion("text", "IN", filtered);
            Criterion[] crits1 = Arrays.copyOf(crits, crits.length + 1);
            crits1[crits.length] = inFilteredCriterion;
            List<String> sqlResults = wordDao.listAllWordsMentioned(Criteria.of(crits1));
            return sqlResults;
        }
    }


}
