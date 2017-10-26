package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <E>
 * @param <PK>
 * @since skean 2.1
 */
public class SinglePKCrudFunctions<E, PK> extends CrudFunctions<E> {

    private Supplier<String> pkNameSupplier;
    private Function<E, PK> pkGetter;
    private Function<PK, E> oneReader;
    private Function<E, E> oneCreatorWithReturn;
    private BiFunction<E, PK, Integer> oneUpdater;
    private BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater;
    private Function<PK, Integer> oneDeleter;

    protected SinglePKCrudFunctions() {
        super();
        setPkNameSupplier(CrudFunctionsUtils::raiseNotImplemented);
        setPkGetter(CrudFunctionsUtils::raiseNotImplemented);
        setOneReader(CrudFunctionsUtils::raiseNotImplemented);
        setOneUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setOnePartialUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setOneDeleter(CrudFunctionsUtils::raiseNotImplemented);
        resetOneCreatorWithReturn();
    }

    protected void resetOneCreatorWithReturn(){
        setOneCreatorWithReturn(e -> {
            getOneCreator().apply(e);
            return getOneReader().apply(getPkGetter().apply(e));
        });
    }

    public Function<PK, E> getOneReader() {
        return oneReader;
    }

    public void setOneReader(Function<PK, E> oneReader) {
        this.oneReader = oneReader;
    }

    public Function<E, E> getOneCreatorWithReturn() {
        return oneCreatorWithReturn;
    }

    public void setOneCreatorWithReturn(Function<E, E> oneCreatorWithReturn) {
        this.oneCreatorWithReturn = oneCreatorWithReturn;
    }

    public BiFunction<E, PK, Integer> getOneUpdater() {
        return oneUpdater;
    }

    public void setOneUpdater(BiFunction<E, PK, Integer> oneUpdater) {
        this.oneUpdater = oneUpdater;
    }

    public BiFunction<Map<String, Object>, PK, Integer> getOnePartialUpdater() {
        return onePartialUpdater;
    }

    public void setOnePartialUpdater(BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater) {
        this.onePartialUpdater = onePartialUpdater;
    }

    public Function<PK, Integer> getOneDeleter() {
        return oneDeleter;
    }

    public void setOneDeleter(Function<PK, Integer> oneDeleter) {
        this.oneDeleter = oneDeleter;
    }

    public Supplier<String> getPkNameSupplier() {
        return pkNameSupplier;
    }

    public void setPkNameSupplier(Supplier<String> pkNameSupplier) {
        this.pkNameSupplier = pkNameSupplier;
    }

    public Function<E, PK> getPkGetter() {
        return pkGetter;
    }

    public void setPkGetter(Function<E, PK> pkGetter) {
        this.pkGetter = pkGetter;
    }


}

