package party.threebody.skean.data.query;

import java.util.Map;

/**
 * @since skean 2.1
 */
public class Criteria {

    private Criterion[] criteria;

    protected Criteria() {

    }

    public Criteria(Criterion[] criteria) {
        this.criteria = criteria;
    }

    public Criterion[] getCriteria() {
        return criteria;
    }

    public void setCriteria(Criterion[] criteria) {
        this.criteria = criteria;
    }


}
