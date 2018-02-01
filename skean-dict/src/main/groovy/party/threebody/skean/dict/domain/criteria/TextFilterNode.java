package party.threebody.skean.dict.domain.criteria;

import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Operator;

public class TextFilterNode extends FilterNode {
    private static String NAME = "text";
    private Operator opt;

    public Operator getOpt() {
        return opt;
    }

    public void setOpt(Operator opt) {
        this.opt = opt;
    }

    public BasicCriterion toBasicCriterion() {
        // TODO a static criteriaBuilder
        return new BasicCriterion(NAME, opt.getExpression(), getVal());
    }

}
