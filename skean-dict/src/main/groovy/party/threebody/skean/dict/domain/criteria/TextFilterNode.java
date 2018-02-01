package party.threebody.skean.dict.domain.criteria;

import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Operator;

public class TextFilterNode extends FilterNode {
    private static String NAME = "text";
    private String opt;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public BasicCriterion toBasicCriterion() {
        return new BasicCriterion(NAME, opt, getVal());
    }

}
