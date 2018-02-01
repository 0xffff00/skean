package party.threebody.skean.dict.domain.criteria;

public class MapNode extends CritTreeNode {
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        ref(4), attr(7),
        sub(9), inst(8), subs(3), subt(3),
        sup(3), def(2), sups(2), supt(2);
        private int cost;

        Type(int cost) {
            this.cost = cost;
        }
    }
}
