package party.threebody.skean.dict.domain.criteria;

public class FilterNode extends CritTreeNode {

    private Object val;

    public Object getVal() {
        return val;
    }

    public String getValStr() {
        return val == null ? null : val.toString();
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
