package party.threebody.skean.mvc.generic;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.BasicCriterion;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.core.query.SortingField;

class QueryParamsBuildUtils {

	public static QueryParamsSuite buildQueryParamsSuite(String criteria, String orders, Integer pageIndex,
			Integer pageLength) {
		// build criteria
		BasicCriterion[] criterionArr = CriterionBuildUtils.buildBasicCriterionArray(criteria);
		// build sortingFields
		SortingField[] sortingFields = null;
		if (StringUtils.isNotEmpty(orders)) {
			String[] strs = orders.split(",");
			int n = strs.length;
			sortingFields = new SortingField[n];
			for (int i = 0; i < n; i++) {
				if (strs[i].length() > 0) {
					sortingFields[i] = new SortingField(strs[i].substring(1), strs[i].charAt(0) == '-');
				}
			}
		}

		return new QueryParamsSuite(criterionArr, sortingFields, pageIndex, pageLength);
	}

	public static QueryParamsSuite buildQueryParamsSuite(Map<String, String> paramsMap) {
		Integer pageIndex = getIntValueFromMap(paramsMap, "p", "page");
		Integer pageLength = getIntValueFromMap(paramsMap, "l", "len", "per_page");
		String orders = getStrValueFromMap(paramsMap, "o", "orders");
		String criteria = getStrValueFromMap(paramsMap, "c", "crit", "criteria");
		return buildQueryParamsSuite(criteria, orders, pageIndex, pageLength);
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