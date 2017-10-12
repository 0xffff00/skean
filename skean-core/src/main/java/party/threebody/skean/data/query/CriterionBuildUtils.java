package party.threebody.skean.data.query;

import party.threebody.skean.collections.Maps;
import party.threebody.skean.collections.Sets;
import party.threebody.skean.lang.ObjectMappers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CriterionBuildUtils {

    private static final Set<String> RESERVED_NAMES = Sets.of(
            "p", "page", "l", "limit", "o", "orders", "f", "offset");
    private static final Pattern RE_LEGAL_PARAM_NAME = Pattern.compile("^[A-Za-z_][A-Za-z0-9_\\$\\.]*$");

    private static final Map<String, String> TAIL2OPT_MAP = Maps.ofKeysAndVals(
            new String[]{"_LT", "_GT", "_LE", "_GE", "_K", "_KL", "_KR", "_NK", "_NKL", "_NKR", "_IN", "_NIN"},
            new String[]{Operators.LT, Operators.GT, Operators.LE, Operators.GE, Operators.K, Operators.KL, Operators.KR, Operators.NK, Operators.NKL, Operators.NKR, Operators.IN, Operators.NIN}
    );

    private CriterionBuildUtils() {
    }


    public static BasicCriterion[] buildBasicCriterionArrayByWrappedObject(String criteria) {
        if (criteria == null) {
            return null;
        }
        try {
            return ObjectMappers.DEFAULT.readerFor(BasicCriterionVO[].class).readValue(criteria);
        } catch (IOException e) {
            throw new SkeanDataException("IO error occurred in buildBasicCriterionArray", e);
        }
    }


    protected static boolean isLegalParamName(String name) {
        return !RESERVED_NAMES.contains(name) && RE_LEGAL_PARAM_NAME.matcher(name).find();
    }


    public static BasicCriterion[] buildBasicCriterionArray(Map<String, String> paramsMap) {
        return buildBasicCriterionArray(paramsMap, null);

    }

    public static BasicCriterion[] buildBasicCriterionArray(Map<String, String> paramsMap,
                                                            Collection<String> paramNameWhiteList) {
        if (paramsMap == null) {
            return null;
        }
        final Predicate<String> whiteListChecker = paramNameWhiteList == null ? (s -> true) :
                paramNameWhiteList::contains;

        return paramsMap
                .keySet().stream()
                .filter(whiteListChecker)
                .filter(CriterionBuildUtils::isLegalParamName)
                .map(pname -> toBasicCriterionByExtendingTail(pname, paramsMap.get(pname)))
                .toArray(BasicCriterion[]::new);

    }

    private static BasicCriterion toBasicCriterionByExtendingTail(String paramName, String paramValue) {
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

class BasicCriterionVO extends BasicCriterion {

    public void setO(String o) {
        setOperator(o);
    }

    public void setN(String n) {
        setName(n);
    }

    public void setV(Object v) {
        setValue(v);
    }

    public String getO() {
        return getOperator();
    }

    public String getN() {
        return getName();
    }

    public Object getV() {
        return getValue();
    }

}