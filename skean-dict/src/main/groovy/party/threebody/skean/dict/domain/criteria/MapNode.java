package party.threebody.skean.dict.domain.criteria;

import java.util.List;

public class MapNode extends CritTreeNode {
    private Mapper mapper;
    private List<CritTreeNode> children;

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public List<CritTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<CritTreeNode> children) {
        this.children = children;
    }
    public enum Mapper {
        ref(4), attr(7),
        sub(9), inst(8), subs(3), subt(3),
        sup(3), def(2), sups(2), supt(2);
        private int cost;

        Mapper(int cost) {
            this.cost = cost;
        }
    }
}
