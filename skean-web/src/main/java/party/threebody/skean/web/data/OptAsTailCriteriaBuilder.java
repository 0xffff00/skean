/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.web.data;

import org.apache.commons.lang3.StringUtils;
import party.threebody.skean.collections.Sets;
import party.threebody.skean.data.query.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Operator as tail
 */
public class OptAsTailCriteriaBuilder implements CriteriaBuilder {

    private SkeanWebConfig config;

    private Set<String> reservedVarNamesCache;

    private static final Pattern RE_LEGAL_PARAM_NAME = Pattern.compile("^[A-Za-z_][A-Za-z0-9_\\$\\.]*$");


    public OptAsTailCriteriaBuilder(SkeanWebConfig config) {
        this.config = config;
    }

    @Override
    public Criteria toCriteria(Map<String, Object> paramsMap) {
        // build criteria
        BasicCriterion[] criterionArr = buildBasicCriterionArray(paramsMap, null);
        return Criteria.of(criterionArr);
    }

    @Override
    public CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap) {
        return toCriteriaAndSortingAndPaging(paramsMap, null);
    }

    /**
     * @param paramsMap          nullable, a parameter Map
     * @param paramNamewhiteList nullable, white list to filter keys of the parameter Map, if null filter disabled
     * @return not null
     */
    @Override
    public CriteriaAndSortingAndPaging toCriteriaAndSortingAndPaging(Map<String, Object> paramsMap,
                                                                     Collection<String> paramNamewhiteList) {
        if (paramsMap == null) {
            return CriteriaAndSortingAndPaging.EMPTY;
        }
        // build sortingFields
        SortingField[] sortingFields = buildSortingFields(paramsMap);
        // build pagingInfo
        PagingInfo pagingInfo = buildPagingInfo(paramsMap);
        // build criteria
        BasicCriterion[] criterionArr = buildBasicCriterionArray(paramsMap, paramNamewhiteList);
        return new CriteriaAndSortingAndPaging(criterionArr, sortingFields, pagingInfo);
    }


    private static String getStrValueFromMap(Map<String, Object> map, Collection<String> possibleKeys) {
        for (String k : possibleKeys) {
            Object v = map.get(k);
            if (v == null) {
                continue;
            }
            String s = String.valueOf(map.get(k));
            if (!s.isEmpty()) {
                return s;
            }
        }
        return null;
    }

    private static Integer getIntValueFromMap(Map<String, Object> map, Collection<String> possibleKeys) {
        for (String k : possibleKeys) {
            Object v = map.get(k);
            if (v == null) {
                return null;
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

    private PagingInfo buildPagingInfo(Map<String, Object> paramsMap) {
        final Integer pageIndex = getIntValueFromMap(paramsMap, config.getCriteriaVarName().getPageIndex());
        final Integer pageOffset = getIntValueFromMap(paramsMap, config.getCriteriaVarName().getPageOffset());
        final Integer pageLimit = getIntValueFromMap(paramsMap, config.getCriteriaVarName().getPageLimit());
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

    private SortingField[] buildSortingFields(Map<String, Object> paramsMap) {
        final String orders = getStrValueFromMap(paramsMap, config.getCriteriaVarName().getOrders());
        return buildSortingFields(orders);
    }

    private SortingField[] buildSortingFields(String params) {
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

    private SortingField buildSortingField(String param) {
        if (param.isEmpty()) {
            return null;
        }
        String s0 = param.substring(0, 1);
        if (config.getCriteriaVarName().getOrdersDescPrefixes().contains(s0)) {
            return new SortingField(param.substring(1), true);
        }
        if (config.getCriteriaVarName().getOrdersAscPrefixes().contains(s0)) {
            return new SortingField(param.substring(1), false);
        }
        return new SortingField(param, false);
    }

    /**
     * synchronization is not an issue, since config is immutable.
     */
    private Set<String> getAllReservedVarNames() {
        if (reservedVarNamesCache == null) {
            reservedVarNamesCache = Sets.of(
                    config.getCriteriaVarName().getPageIndex(),
                    config.getCriteriaVarName().getPageLimit(),
                    config.getCriteriaVarName().getPageOffset(),
                    config.getCriteriaVarName().getOrders()
            );
        }
        return reservedVarNamesCache;
    }

    private boolean isLegalParamName(String name) {
        return !getAllReservedVarNames().contains(name) && RE_LEGAL_PARAM_NAME.matcher(name).find();
    }


    @Override
    public BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap) {
        return buildBasicCriterionArray(paramsMap, null);

    }

    @Override
    public BasicCriterion[] buildBasicCriterionArray(Map<String, Object> paramsMap,
                                                     Collection<String> paramNameWhiteList) {
        if (paramsMap == null) {
            return null;
        }
        final Predicate<String> whiteListChecker = paramNameWhiteList == null
                ? (s -> true)
                : paramNameWhiteList::contains;

        return paramsMap
                .keySet().stream()
                .filter(whiteListChecker)
                .filter(this::isLegalParamName)
                .map(pname -> toBasicCriterion(pname, paramsMap.get(pname)))
                .toArray(BasicCriterion[]::new);

    }

    private String getRenderedVarSuffix(String x) {
        String template = config.getCriteriaVarName().getExtesibleVarSuffixTemplate();
        if (template == null) {
            return x;
        }
        return template.replace("{x}", x);
    }

    /**
     * convert displayed url request params to actual.
     * <pre>
     * for example:
     *   toBasicCriterion("age_GE",18) ---> new BasicCriterion("age","GE",18)
     *   toBasicCriterion("state_IN", "A,B,C") ---> new BasicCriterion("age","IN",["A","B","C"])
     * </pre>
     *
     * @param paramName
     * @param paramValue
     * @return
     */
    private BasicCriterion toBasicCriterion(String paramName, Object paramValue) {
        Map<String, String> displayed2real = config.getCriteriaVarName().getOperators();
        Object realParamVal = paramValue;
        String dlmt = config.getUriVarMultiValuesDelimitter();
        if (dlmt != null) {
            if (paramValue != null && paramValue instanceof String) {
                String s = (String) paramValue;
                if (s.contains(dlmt)) {
                    realParamVal = Arrays.asList(s.split(dlmt));
                }
            }
        }

        for (String displayed : displayed2real.keySet()) {
            String rendered = getRenderedVarSuffix(displayed);
            if (paramName.endsWith(rendered)) {
                String realParamName = paramName.substring(0, paramName.length() - rendered.length());
                String real = displayed2real.get(displayed);
                return new BasicCriterion(realParamName, real, realParamVal);
            }
        }
        // default to equals
        return new BasicCriterion(paramName, realParamVal);
    }
}