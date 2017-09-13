package party.threebody.skean.core.query;

import party.threebody.skean.core.SkeanException;
import party.threebody.skean.collections.Sets;
import party.threebody.skean.lang.StringCases;
import party.threebody.skean.lang.ObjectMappers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CriterionBuildUtils {
    private CriterionBuildUtils() {
    }


    public static BasicCriterion[] buildBasicCriterionArray(String criteria) {
        if (criteria == null) {
            return null;
        }
        try {
            return ObjectMappers.DEFAULT.readerFor(BasicCriterionVO[].class).readValue(criteria);
        } catch (IOException e) {
            throw new SkeanException("IO error occurred in buildBasicCriterionArray", e);
        }
    }


    private static final Set<String> RESERVED_NAMES = Sets.of("p", "page", "l", "len", "o", "orders");
    private static final Pattern RE_LEGAL_PARAM_NAME = Pattern.compile("^[A-Za-z_][A-Za-z0-9_\\$\\.]*$");

    protected static boolean isLegalParamName(String name) {
        return !RESERVED_NAMES.contains(name) && RE_LEGAL_PARAM_NAME.matcher(name).find();
    }

    /**
     * TODO support more complex param naming
     */
    public static BasicCriterion[] buildBasicCriterionArray(Map<String, String> paramsMap) {
        return buildBasicCriterionArray(paramsMap,null);

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
                .map(pname -> new BasicCriterion(StringCases.camelToSnake(pname), paramsMap.get(pname)))
                .toArray(BasicCriterion[]::new);

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