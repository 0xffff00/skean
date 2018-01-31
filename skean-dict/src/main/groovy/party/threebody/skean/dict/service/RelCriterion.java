package party.threebody.skean.dict.service;

import org.apache.commons.lang3.StringUtils;

public class RelCriterion {
    private RelMapper mapper;
    private String mapperArg;
    private RelPredicate predicate;
    private String predicateArg;

    public static RelCriterion of(String mapperName, String mapperArg, String predicateName, String predicateArg) {
        RelCriterion c = new RelCriterion();
        if (mapperName != null) {
            c.mapper = RelMapper.valueOf(mapperName);
            c.mapperArg = mapperArg;
        }
        c.predicate = RelPredicate.valueOf(predicateName);
        c.predicateArg = predicateArg;
        return c;
    }

    public static RelCriterion of(String paramName, String paramValue) {
        String[] names = StringUtils.split(paramName, '^');
        String[] vals = StringUtils.split(paramValue, '^');
        String mapperName = null;
        String mapperArg = null;
        String predicateName = paramName;
        String predicateArg = paramValue;
        if (names.length >= 2) {
            mapperName = names[0];
            predicateName = names[1];
        }
        if (vals.length >= 2) {
            mapperArg = vals[0];
            predicateArg = vals[0];
        }
        return of(mapperName, mapperArg, predicateName, predicateArg);
    }

    public RelMapper getMapper() {
        return mapper;
    }

    public void setMapper(RelMapper mapper) {
        this.mapper = mapper;
    }

    public String getMapperArg() {
        return mapperArg;
    }

    public void setMapperArg(String mapperArg) {
        this.mapperArg = mapperArg;
    }

    public RelPredicate getPredicate() {
        return predicate;
    }

    public void setPredicate(RelPredicate predicate) {
        this.predicate = predicate;
    }

    public String getPredicateArg() {
        return predicateArg;
    }

    public void setPredicateArg(String predicateArg) {
        this.predicateArg = predicateArg;
    }
}

enum RelPredicate {
    subOf, instanceOf, subsetOf, subtopicOf,
    superOf, definitionOf, supersetOf, supertopicOf
}

enum RelMapper {
    self, attr, rel
}

