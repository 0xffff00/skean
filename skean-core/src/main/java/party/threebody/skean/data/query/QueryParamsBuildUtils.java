package party.threebody.skean.data.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

public class QueryParamsBuildUtils {

    private QueryParamsBuildUtils() {
    }


    /**
     * read parameters only by names: p,l,o,c<br>
     * PLOC is stand for page,len,orders,criteria
     *
     * @param paramsMap
     * @return
     */
    @Deprecated
    public static QueryParamsSuite buildQueryParamsSuiteByPLOC(Map<String, String> paramsMap) {
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(paramsMap);
        // build pagingInfo
        PagingInfo pagingInfo = buildPagingInfo(paramsMap);

        String criteria = getStrValueFromMap(paramsMap, "c", "crit", "criteria");
        // build criteria
        BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArrayByWrappedObject(criteria);
        return new QueryParamsSuite(criterionArr, sortingFields, pagingInfo);
    }


    /**
     * <h1>read parameters by all keys</h1>
     * PLOx means Page,Len,Orders and any other flat eXtensible params
     *
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
     * @param paramsMap          a parameter Map
     * @param paramNamewhiteList white list to filter keys of the parameter Map
     * @return QueryParamsSuite built
     */
    public static QueryParamsSuite buildQueryParamsSuiteByPLOx(Map<String, String> paramsMap, Collection<String> paramNamewhiteList) {
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(paramsMap);
        // build pagingInfo
        PagingInfo pagingInfo = buildPagingInfo(paramsMap);
        // build criteria
        BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArray(paramsMap, paramNamewhiteList);
        return new QueryParamsSuite(criterionArr, sortingFields, pagingInfo);


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
                    throw new SkeanDataException("not numeric value: [" + k + "=" + v + "]", e);
                }
            }
        }
        return null;
    }

    private static PagingInfo buildPagingInfo(Map<String, String> paramsMap) {
        final Integer pageIndex = getIntValueFromMap(paramsMap, "p", "page");
        final Integer pageOffset = getIntValueFromMap(paramsMap, "f", "offset");
        final Integer pageLimit = getIntValueFromMap(paramsMap, "l", "limit", "per_page");
        return buildPagingInfo(pageIndex, pageOffset, pageLimit);
    }

    private static PagingInfo buildPagingInfo(Integer pageIndex, Integer pageOffset, Integer pageLimit) {
        if (pageLimit == null) {
            if (pageIndex != null && pageOffset != null) {
                throw new SkeanDataException("pageLimit is required for pageIndex or pageOffset.");
            }
            return PagingInfo.NA;
        }
        if (pageIndex != null && pageOffset != null) {
            throw new SkeanDataException("pageIndex and pageOffset cannot be passed at the same time.");
        }
        if (pageIndex != null) {
            return PagingInfo.ofPageNum(pageIndex, pageLimit);
        } else {
            return PagingInfo.ofOffset(pageOffset, pageLimit);
        }

    }

    private static SortingField[] buildSortingFields(Map<String, String> paramsMap) {
        final String orders = getStrValueFromMap(paramsMap, "o", "orders");
        return buildSortingFields(orders);
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
}