package party.threebody.skean.dict.domain.criteria;

public class RelFilterNode extends FilterNode {
    private Pred pred;

    public Pred getPred() {
        return pred;
    }

    public void setPred(Pred pred) {
        this.pred = pred;
    }

    public enum Pred {
        subOf(9), instOf(8), subsOf(3), subtOf(3),
        supOf(3), defOf(2), supsOf(2), suptOf(2);
        private int cost;

        Pred(int cost) {
            this.cost = cost;
        }
    }
}
