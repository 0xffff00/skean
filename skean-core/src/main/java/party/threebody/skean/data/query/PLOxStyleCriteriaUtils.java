package party.threebody.skean.data.query;

import org.apache.commons.lang3.StringUtils;
import party.threebody.skean.collections.Maps;
import party.threebody.skean.collections.Sets;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * PLOx means pageNum(P),pageLength(L),Orders(O) and any other user-defined flat eXtensible params(X)
 */
public class PLOxStyleCriteriaUtils {

    private static final String[] PAGE_INDEX_VAR_NAMES = {"p", "page"};
    private static final String[] PAGE_OFFSET_VAR_NAMES = {"f", "offset"};
    private static final String[] PAGE_LIMIT_VAR_NAMES = {"l", "limit", "per_page"};
    private static final String[] ORDERS_VAR_NAMES = {"o", "orders"};
    private static final Set<String> RESERVED_VAR_NAMES = Sets.of(
            "p", "page", "l", "limit", "o", "orders", "f", "offset");
    private static final Pattern RE_LEGAL_PARAM_NAME = Pattern.compile("^[A-Za-z_][A-Za-z0-9_\\$\\.]*$");

    private static final Map<String, String> TAIL2OPT_MAP = Maps.ofKeysAndVals(
            new String[]{"_LT", "_GT", "_LE", "_GE", "_K", "_KL", "_KR", "_NK", "_NKL", "_NKR", "_IN", "_NIN"},
            new String[]{Operators.LT, Operators.GT, Operators.LE, Operators.GE, Operators.K, Operators.KL,
                    Operators.KR, Operators.NK, Operators.NKL, Operators.NKR, Operators.IN, Operators.NIN}
    );

    private PLOxStyleCriteriaUtils() {
    }

    /**
     * <h1>read parameters by all keys</h1>
     *
     * @param paramsMap a parameter Map
     * @return Criteria built
     */
    public static Criteria toCriteria(Map<String, Object> paramsMap) {
        // build criteria
        BasicCriterion[] criterionArr = buildBasicCriterionArray(paramsMap, null);
        return new Criteria(criterionArr);
    }

    /**
     * <h1>read parameters by all keys</h1>
     *
     * @param paramsMap a parameter Map
     * @return CriteriaAndSortingAndPaging built
     */
    public static CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap) {
        return toCriteriaAndSortingAndPaging(paramsMap, null);
    }

    /**
     * <h1>read parameters by keys restricted by a white list</h1>
     *
     * @param paramsMap          a parameter Map
     * @param paramNamewhiteList white list to filter keys of the parameter Map
     * @return CriteriaAndSortingAndPaging built
     */
    public static CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap,
                                                                            Collection<String> paramNamewhiteList) {
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(paramsMap);
        // build pagingInfo
        PagingInfo pagingInfo = buildPagingInfo(paramsMap);
        // build criteria
        BasicCriterion[] criterionArr = buildBasicCriterionArray(paramsMap, paramNamewhiteList);
        return new CriteriaAndSortingAndPaging(criterionArr, sortingFields, pagingInfo);
    }


    private static String getStrValueFromMap(Map<String, Object> map, String... possibleKeys) {
        for (String k : possibleKeys) {
            String v = String.valueOf(map.get(k));
            if (StringUtils.isNotEmpty(v)) {
                return v;
            }
        }
        return null;
    }

    private static Integer getIntValueFromMap(Map<String, Object> map, String... possibleKeys) {
        for (String k : possibleKeys) {
            Object v = map.get(k);
            if (v == null) {
                throw new SkeanDataException("null numeric value: [" + k + "]");
            }
            if (v instanceof Number) {
                return ((Number) v).intValue();
            }
            if (v instanceof String) {
                try {
                    return Integer.valueOf((String) v);
                } catch (NumberFormatException e) {
                    throw new SkeanDataException("not numeric value: [" + k + "=" + v + "]");
                }
            }
            throw new SkeanDataException("not numeric value: [" + k + "=" + v + "]");
        }
        return null;
    }

    private static PagingInfo buildPagingInfo(Map<String, Object> paramsMap) {
        final Integer pageIndex = getIntValueFromMap(paramsMap, PAGE_INDEX_VAR_NAMES);
        final Integer pageOffset = getIntValueFromMap(paramsMap, PAGE_OFFSET_VAR_NAMES);
        final Integer pageLimit = getIntValueFromMap(paramsMap, PAGE_LIMIT_VAR_NAMES);
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

    private static SortingField[] buildSortingFields(Map<String, Object> paramsMap) {
        final String orders = getStrValueFromMap(paramsMap, ORDERS_VAR_NAMES);
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


    protected static boolean isLegalParamName(String name) {
        return !RESERVED_VAR_NAMES.contains(name) && RE_LEGAL_PARAM_NAME.matcher(name).find();
    }


    public static BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap) {
        return buildBasicCriterionArray(paramsMap, null);

    }

    public static BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap,
                                                            Collection<String> paramNameWhiteList) {
        if (paramsMap == null) {
            return null;
        }
        final Predicate<String> whiteListChecker = paramNameWhiteList == null ? (s -> true) :
                paramNameWhiteList::contains;

        return paramsMap
                .keySet().stream()
                .filter(whiteListChecker)
                .filter(PLOxStyleCriteriaUtils::isLegalParamName)
                .map(pname -> toBasicCriterionByExtendingTail(pname, paramsMap.get(pname)))
                .toArray(BasicCriterion[]::new);

    }

    private static BasicCriterion toBasicCriterionByExtendingTail(String paramName, Object paramValue) {
        for (String key : TAIL2OPT_MAP.keySet()) {
            if (paramName.endsWith(key)) {
                String realParamName = paramName.substring(0, paramName.length() - key.length());
                String operator = TAIL2OPT_MAP.get(key);
                return new BasicCriterion(realParamName, operator, paramValue);
            }
        }
        return new BasicCriterion(paramName, paramValue);
    }
}