package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.mvc.dao.AbstractCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * because of unwareness of primary keys, cannot read/delete/update one exactly.
 *
 * @param <E>
 * @since skean 2.1
 */
public class CrudFunctions<E> {

    private Function<CriteriaAndSortingAndPaging, List<E>> listReader;
    private Function<Criteria, Integer> countReader;
    private Function<E, Integer> oneCreator;
    private BiFunction<E, Criteria, Integer> updater;
    private BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater;
    private Function<Criteria, Integer> deleter;
    private boolean batchDeleteEnabled;
    private boolean batchUpdateEnabled;

    protected CrudFunctions() {
        setListReader(CrudFunctionsUtils::raiseNotImplemented);
        setCountReader(CrudFunctionsUtils::raiseNotImplemented);
        setOneCreator(CrudFunctionsUtils::raiseNotImplemented);
        setUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setPartialUpdater(CrudFunctionsUtils::raiseNotImplemented);
        setDeleter(CrudFunctionsUtils::raiseNotImplemented);
        setBatchDeleteEnabled(false);
        setBatchUpdateEnabled(false);
    }



    public Function<CriteriaAndSortingAndPaging, List<E>> getListReader() {
        return listReader;
    }

    public void setListReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
        this.listReader = listReader;
    }

    public Function<Criteria, Integer> getCountReader() {
        return countReader;
    }

    public void setCountReader(Function<Criteria, Integer> countReader) {
        this.countReader = countReader;
    }

    public Function<E, Integer> getOneCreator() {
        return oneCreator;
    }

    public void setOneCreator(Function<E, Integer> oneCreator) {
        this.oneCreator = oneCreator;
    }

    public BiFunction<E, Criteria, Integer> getUpdater() {
        return updater;
    }

    public void setUpdater(BiFunction<E, Criteria, Integer> updater) {
        this.updater = updater;
    }

    public BiFunction<Map<String, Object>, Criteria, Integer> getPartialUpdater() {
        return partialUpdater;
    }

    public void setPartialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
        this.partialUpdater = partialUpdater;
    }

    public Function<Criteria, Integer> getDeleter() {
        return deleter;
    }

    public void setDeleter(Function<Criteria, Integer> deleter) {
        this.deleter = deleter;
    }

    public boolean isBatchDeleteEnabled() {
        return batchDeleteEnabled;
    }

    public void setBatchDeleteEnabled(boolean batchDeleteEnabled) {
        this.batchDeleteEnabled = batchDeleteEnabled;
    }

    public boolean isBatchUpdateEnabled() {
        return batchUpdateEnabled;
    }

    public void setBatchUpdateEnabled(boolean batchUpdateEnabled) {
        this.batchUpdateEnabled = batchUpdateEnabled;
    }


}
