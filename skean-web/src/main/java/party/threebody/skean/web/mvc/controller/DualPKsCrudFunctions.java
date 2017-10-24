package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.lang.TriFunction;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DualPKsCrudFunctions<E, PK1, PK2> extends CrudFunctions<E> {

    protected Function<E, PK1> pk1Getter;
    protected Function<E, PK2> pk2Getter;
    protected BiFunction<PK1, PK2, E> oneReader;
    protected Function<E, E> creatorWithReturn;
    protected TriFunction<E, PK1, PK2, Integer> entireUpdater2;
    protected TriFunction<Map<String, Object>, PK1, PK2, Integer> partialUpdater2;
    protected BiFunction<PK1, PK2, Integer> deleter2;

    public Function<E, PK1> getPk1Getter() {
        return pk1Getter;
    }

    public void setPk1Getter(Function<E, PK1> pk1Getter) {
        this.pk1Getter = pk1Getter;
    }

    public Function<E, PK2> getPk2Getter() {
        return pk2Getter;
    }

    public void setPk2Getter(Function<E, PK2> pk2Getter) {
        this.pk2Getter = pk2Getter;
    }

    public BiFunction<PK1, PK2, E> getOneReader() {
        return oneReader;
    }

    public void setOneReader(BiFunction<PK1, PK2, E> oneReader) {
        this.oneReader = oneReader;
    }

    public Function<E, E> getCreatorWithReturn() {
        return creatorWithReturn;
    }

    public void setCreatorWithReturn(Function<E, E> creatorWithReturn) {
        this.creatorWithReturn = creatorWithReturn;
    }

    public TriFunction<E, PK1, PK2, Integer> getEntireUpdater2() {
        return entireUpdater2;
    }

    public void setEntireUpdater2(TriFunction<E, PK1, PK2, Integer> entireUpdater2) {
        this.entireUpdater2 = entireUpdater2;
    }

    public TriFunction<Map<String, Object>, PK1, PK2, Integer> getPartialUpdater2() {
        return partialUpdater2;
    }

    public void setPartialUpdater2(TriFunction<Map<String, Object>, PK1, PK2, Integer> partialUpdater2) {
        this.partialUpdater2 = partialUpdater2;
    }

    public BiFunction<PK1, PK2, Integer> getDeleter2() {
        return deleter2;
    }

    public void setDeleter2(BiFunction<PK1, PK2, Integer> deleter2) {
        this.deleter2 = deleter2;
    }
}

