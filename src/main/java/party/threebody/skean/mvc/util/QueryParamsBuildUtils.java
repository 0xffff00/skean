package party.threebody.skean.mvc.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.query.SortingField;

public class QueryParamsBuildUtils {

	private QueryParamsBuildUtils() {
	}

	public static QueryParamsSuite buildQueryParamsSuite(String criteria, String orders, Integer pageIndex,
			Integer pageLength) {
		// build criteria
		BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArray(criteria);
		// build sortingFields
		SortingField[] sortingFields = buildSortingFields(orders);
		return new QueryParamsSuite(criterionArr, sortingFields, pageIndex, pageLength);
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

	public static QueryParamsSuite buildQueryParamsSuite(Map<String, String> paramsMap) {
		Integer pageIndex = getIntValueFromMap(paramsMap, "p", "page");
		Integer pageLength = getIntValueFromMap(paramsMap, "l", "len", "per_page");
		String orders = getStrValueFromMap(paramsMap, "o", "orders");
		String criteria = getStrValueFromMap(paramsMap, "c", "crit", "criteria");
		QueryParamsSuite qps = buildQueryParamsSuite(criteria, orders, pageIndex, pageLength);
		return qps;
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