package party.threebody.skean.dict.domain.criteria;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import party.threebody.skean.misc.SkeanNotImplementedException;

import java.util.Arrays;
import java.util.List;

/**
 * a Criteria Tree containing Filter and Map
 * MapNode: a non-leaf node which has children:     -- m(x) -->
 * FilterNode: a leaf node                    :     -- f --> every ch/ch0
 * ch0 is like { o:'EQ', v:20 }, { v:20 }
 * abbreviation: f - filter, m - map, ch - child, o - opt - operator, x - arg of f or m, v - value
 * <p>
 * {m: 'attr', x: 'age',ch: [{o:'EQ', v:20}]}   //
 * == {m: 'attr', x: 'age', o0:'EQ', v0: 20}    // if only child is a FilterNode, can be written to o0,v0
 * == {m: 'attr', x: 'age', v0: 20}             //'EQ' can be omitted
 * <p>
 * {m: 'attr', x: 'star',ch: [{o:'GT', v:3},{o:'LT', v:5}]}
 * {m: 'super', x: 'school',ch:[{f:'instOf', v:'beijing schools'}]}
 * {o:'LT', v:5}
 * {f:'instOf', v:'beijing schools'}
 * ['
 */
@JsonDeserialize(builder = CritTreeNodeBuilder.class)
public abstract class CritTreeNode {
    private String x;

    /**
     * arg of functor like mapper/predicate
     * @return
     */
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
}

@JsonPOJOBuilder
class CritTreeNodeBuilder {
    private List<CritTreeNode> ch;
    private String o;
    private Object v;
    private String f;
    private String m;
    private String x;
    private String o0;
    private String v0;


    public CritTreeNode build() {
        if (m != null) {
            MapNode n = new MapNode();
            n.setMapper(MapNode.Mapper.valueOf(m));
            n.setX(x);
            if (v0 != null) {
                TextFilterNode ch0 = new TextFilterNode();
                ch0.setOpt(o0);
                ch0.setVal(v0);
                n.setChildren(Arrays.asList(ch0));
            }else{
                n.setChildren(ch);
            }
            return n;
        }

        if (f != null) {
            RelFilterNode n = new RelFilterNode();
            n.setPred(RelFilterNode.Pred.valueOf(f));
            n.setVal(v);
            return n;
        }
        if (o != null) {
            TextFilterNode n = new TextFilterNode();
            n.setOpt(o);
            n.setVal(v);
            return n;
        }
        throw new SkeanNotImplementedException("only support 'f|m|o'");
    }

    public CritTreeNodeBuilder withO(String o) {
        this.o = o;
        return this;
    }
    public CritTreeNodeBuilder withV(Object v) {
        this.v = v;
        return this;
    }

    public CritTreeNodeBuilder withF(String f) {
        this.f = f;
        return this;
    }

    public CritTreeNodeBuilder withM(String m) {
        this.m = m;
        return this;
    }
    public CritTreeNodeBuilder withX(String s) {
        this.x = s;
        return this;
    }
    public CritTreeNodeBuilder withV0(String s) {
        this.v0 = s;
        return this;
    }

    public CritTreeNodeBuilder withO0(String s) {
        this.o0 = s;
        return this;
    }

    public CritTreeNodeBuilder withCh(List<CritTreeNode> ch) {
        this.ch = ch;
        return this;
    }

}
