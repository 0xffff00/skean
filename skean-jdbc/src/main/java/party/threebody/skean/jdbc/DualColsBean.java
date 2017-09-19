package party.threebody.skean.jdbc;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = DualColsBeanSerializer.class)
public class DualColsBean<F0, F1> {
    protected F0 f0;
    protected F1 f1;

    public DualColsBean(F0 f0, F1 f1) {
        this.f0 = f0;
        this.f1 = f1;
    }

    public F0 get0() {
        return f0;
    }

    public F1 get1() {
        return f1;
    }

    @Override
    public String toString() {
        return "(" + f0 + "," + f1 + ")";
    }
}
