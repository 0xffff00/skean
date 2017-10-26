package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.mvc.dao.MultiPKsCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultiPKsCrudFunctions<E> extends CrudFunctions<E> {

    private Supplier<List<String>> pkNamesSupplier;
    private Function<E, List<Object>> pkGetter;
    private Function<List<Object>, E> oneReader;
    private Function<E, E> oneCreatorWithReturn;
    private BiFunction<E, List<Object>, Integer> oneUpdater;
    private BiFunction<Map<String, Object>, List<Object>, Integer> onePartialUpdater;
    private Function<List<Object>, Integer> oneDeleter;


    public Function<E, List<Object>> getPkGetter() {
        return pkGetter;
    }

    protected MultiPKsCrudFunctions() {
        super();
        setPkNamesSupplier(CrudFunctionsUtils::raiseNotImplemented);
        setPkGetter(CrudFunctionsUtils::raiseNotImplemented);
        setOneReader(CrudFunctionsUtils::raiseNotImplemented);
        setOneUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setOnePartialUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setOneDeleter(CrudFunctionsUtils::raiseNotImplemented);
        setOneCreatorWithReturn(CrudFunctionsUtils::raiseNotImplemented);
    }

    protected void resetOneCreatorWithReturn(){
        setOneCreatorWithReturn(e -> {
            getOneCreator().apply(e);
            return getOneReader().apply(getPkGetter().apply(e));
        });
    }

    public Supplier<List<String>> getPkNamesSupplier() {
        return pkNamesSupplier;
    }

    public void setPkNamesSupplier(Supplier<List<String>> pkNamesSupplier) {
        this.pkNamesSupplier = pkNamesSupplier;
    }

    public void setPkGetter(Function<E, List<Object>> pkGetter) {
        this.pkGetter = pkGetter;
    }

    public Function<List<Object>, E> getOneReader() {
        return oneReader;
    }

    public void setOneReader(Function<List<Object>, E> oneReader) {
        this.oneReader = oneReader;
    }

    public Function<E, E> getOneCreatorWithReturn() {
        return oneCreatorWithReturn;
    }

    public void setOneCreatorWithReturn(Function<E, E> oneCreatorWithReturn) {
        this.oneCreatorWithReturn = oneCreatorWithReturn;
    }

    public BiFunction<E, List<Object>, Integer> getOneUpdater() {
        return oneUpdater;
    }

    public void setOneUpdater(BiFunction<E, List<Object>, Integer> oneUpdater) {
        this.oneUpdater = oneUpdater;
    }

    public BiFunction<Map<String, Object>, List<Object>, Integer> getOnePartialUpdater() {
        return onePartialUpdater;
    }

    public void setOnePartialUpdater(BiFunction<Map<String, Object>, List<Object>, Integer> onePartialUpdater) {
        this.onePartialUpdater = onePartialUpdater;
    }

    public Function<List<Object>, Integer> getOneDeleter() {
        return oneDeleter;
    }

    public void setOneDeleter(Function<List<Object>, Integer> oneDeleter) {
        this.oneDeleter = oneDeleter;
    }

}
