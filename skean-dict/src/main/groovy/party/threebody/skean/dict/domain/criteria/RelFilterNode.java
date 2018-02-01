package party.threebody.skean.dict.domain.criteria;

public class RelFilterNode extends FilterNode {
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        subOf(9), instOf(8), subsOf(3), subtOf(3),
        supOf(3), defOf(2), supsOf(2), suptOf(2);
        private int cost;

        Type(int cost) {
            this.cost = cost;
        }
    }
}
