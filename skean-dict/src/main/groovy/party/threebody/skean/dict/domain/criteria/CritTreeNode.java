package party.threebody.skean.dict.domain.criteria;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import party.threebody.skean.data.query.Operator;

import java.util.List;

/**
 * a Criteria Tree containing Filter and Map
 * {t: 'attr', v: 'star',ch: [{o:'GT', v:3},{o:'LT', v:5}]}
 * {t: 'super', v: 'school',ch:[{f:'instOf', v:'beijing schools'}]}
 * {o:'LT', v:5}
 * {t:'instOf', v:'beijing schools'}
 * ['
 */
@JsonDeserialize(builder = CritTreeNodeBuilder.class)
public class CritTreeNode {
    private String val;

    public String getVal() {
        return val;
    }

    public CritTreeNode() {
    }

    public void setVal(String val) {
        this.val = val;
    }


}

@JsonPOJOBuilder
class CritTreeNodeBuilder {
    private String v;
    private List<CritTreeNode> ch;
    private String o;
    private String t;

    public CritTreeNodeBuilder withV(String v) {
        this.v = v;
        return this;
    }

    public CritTreeNodeBuilder withO(String o) {
        this.o = o;
        return this;
    }

    public CritTreeNodeBuilder withT(String t) {
        this.t = t;
        return this;
    }

    public CritTreeNodeBuilder withCh(List<CritTreeNode> ch) {
        this.ch = ch;
        return this;
    }

    public CritTreeNode build() {
        if (o != null) {
            Operator opt = Operator.valueOf(o);
            TextFilterNode n = new TextFilterNode();
            n.setOpt(opt);
            n.setVal(v);
            return n;
        }
        if (ch == null) {
            RelFilterNode n = new RelFilterNode();
            n.setType(RelFilterNode.Type.valueOf(t));
            n.setVal(v);
            return n;
        } else {
            MapNode n = new MapNode();
            n.setType(MapNode.Type.valueOf(t));
            n.setChildren(ch);
            n.setVal(v);
            return n;
        }

    }
}
