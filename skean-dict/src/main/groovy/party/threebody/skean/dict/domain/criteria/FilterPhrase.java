package party.threebody.skean.dict.domain.criteria;

import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Operator;

public class FilterPhrase implements WordCriteriaPhrase {
    private Class valueType;    // Integer, String, Double
    private BasicCriterion basicCriterion;

    public BasicCriterion getBasicCriterion() {
        return basicCriterion;
    }
}
