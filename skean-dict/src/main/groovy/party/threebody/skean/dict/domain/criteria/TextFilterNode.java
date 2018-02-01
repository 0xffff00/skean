package party.threebody.skean.dict.domain.criteria;

import party.threebody.skean.data.query.BasicCriterion;

public class TextFilterNode extends FilterNode {
    private String NAME = "text";
    private String opt;

    public enum ValType {
        INT, REAL, STR
    }

    ;
    private ValType valType;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public ValType getValType() {
        return valType;
    }

    public void setValType(ValType valType) {
        this.valType = valType;
    }

    public Object getValObj() {
        switch (valType) {
            case INT:
                return Integer.valueOf(getVal());
            case REAL:
                return Double.valueOf(getVal());
            default:
                return getVal();
        }
    }

    public BasicCriterion toBasicCriterion() {
        return new BasicCriterion(NAME, opt, getValObj());
    }

}
