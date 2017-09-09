package party.threebody.skean.mvc.util;

import org.apache.commons.lang3.StringUtils;
import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.query.SortingField;

import java.util.Collection;
import java.util.Map;

public class QueryParamsBuildUtils {

    private QueryParamsBuildUtils() {
    }


    private static SortingField[] buildSortingFields(String params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        String[] strs = params.split(",");
        int n = strs.length;
        SortingField[] res = new SortingField[n];
        for (int i = 0; i < n; i++) {
            res[i] = buildSortingField(strs[i]);
        }
        return res;
    }

    private static SortingField buildSortingField(String param) {
        if (param.isEmpty()) {
            return null;
        }
        switch (param.charAt(0)) {
            case '-':
            case '!':
                return new SortingField(param.substring(1), true);
            case '+':
                return new SortingField(param.substring(1), false);
            default:
                return new SortingField(param, false);
        }
    }

    public static QueryParamsSuite buildQueryParamsSuiteByPLOC(String criteria,
                                                               String orders,
                                                               Integer pageIndex,
                                                               Integer pageLength) {
        // build criteria
        BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArray(criteria);
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(orders);
        return new QueryParamsSuite(criterionArr, sortingFields, pageIndex, pageLength);
    }

    /**
     * read parameters only by names: p,l,o,c<br>
     * PLOC is stand for page,len,orders,criteria
     *
     * @param paramsMap
     * @return
     */
    public static QueryParamsSuite buildQueryParamsSuiteByPLOC(Map<String, String> paramsMap) {
        Integer pageIndex = getIntValueFromMap(paramsMap, "p", "page");
        Integer pageLength = getIntValueFromMap(paramsMap, "l", "len", "per_page");
        String orders = getStrValueFromMap(paramsMap, "o", "orders");
        String criteria = getStrValueFromMap(paramsMap, "c", "crit", "criteria");
        QueryParamsSuite qps = buildQueryParamsSuiteByPLOC(criteria, orders, pageIndex, pageLength);
        return qps;
    }

    /**
     * <h1>read parameters by all keys</h1>
     * PLOx means Page,Len,Orders and any other flat eXtensible params
     * @param paramsMap a parameter Map
     * @return QueryParamsSuite built
     */
    public static QueryParamsSuite buildQueryParamsSuiteByPLOx(Map<String, String> paramsMap) {
        return buildQueryParamsSuiteByPLOx(paramsMap, null);
    }

    /**
     * TODO need rename
     * <h1>read parameters by keys restricted by a white list</h1>
     * PLOx means Page,Len,Orders and any other flat eXtensible params
     *
     * @param paramsMap a parameter Map
     * @param paramNamewhiteList  white list to filter keys of the parameter Map
     * @return QueryParamsSuite built
     */
    public static QueryParamsSuite buildQueryParamsSuiteByPLOx(Map<String, String> paramsMap, Collection<String> paramNamewhiteList) {
        final Integer pageIndex = getIntValueFromMap(paramsMap, "p", "page");
        Integer pageLength = getIntValueFromMap(paramsMap, "l", "len", "per_page");
        String orders = getStrValueFromMap(paramsMap, "o", "orders");
        // build criteria
        BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArray(paramsMap, paramNamewhiteList);
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(orders);
        return new QueryParamsSuite(criterionArr, sortingFields, pageIndex, pageLength);


    }


    private static String getStrValueFromMap(Map<String, String> map, String... possibleKeys) {
        for (String k : possibleKeys) {
            String v = map.get(k);
            if (StringUtils.isNotEmpty(v)) {
                return v;
            }
        }
        return null;
    }

    private static Integer getIntValueFromMap(Map<String, String> map, String... possibleKeys) {
        for (String k : possibleKeys) {
            String v = map.get(k);
            if (StringUtils.isNotEmpty(v)) {
                try {
                    return Integer.valueOf(v);
                } catch (NumberFormatException e) {
                    throw new SkeanException("not numeric value: [" + k + "=" + v + "]", e);
                }
            }
        }
        return null;
    }

}