package party.threebody.skean.dict.domain.criteria;

import java.util.List;

/**
 * a Criteria Tree containing Filter and Map
 * {m: 'attr', v: 'star',ch: [{f:'GT', v:3},{f:'LT', v:5}]}
 * {m: 'super', v: 'school',ch:[{f:'instOf', v:'beijing schools'}]}
 * {f:'LT', v:5}
 * {f:'instOf', v:'beijing schools'}
 * ['
 */
public class CritTreeNode {
    private String val;
    private List<CritTreeNode> children;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public List<CritTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<CritTreeNode> children) {
        this.children = children;
    }
}
