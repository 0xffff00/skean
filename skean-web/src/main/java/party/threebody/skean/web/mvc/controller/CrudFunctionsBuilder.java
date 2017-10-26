package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class CrudFunctionsBuilder<E> {
    private CrudFunctions<E> crudFunctions;

    protected CrudFunctionsBuilder() {
        crudFunctions = new CrudFunctions<>();
    }

    public CrudFunctionsBuilder<E> listReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
        crudFunctions.setListReader(listReader);
        return this;
    }

    public CrudFunctionsBuilder<E> countReader(Function<Criteria, Integer> countReader) {
        crudFunctions.setCountReader(countReader);
        return this;
    }

    public CrudFunctionsBuilder<E> oneCreator(Function<E, Integer> oneCreator) {
        crudFunctions.setOneCreator(oneCreator);
        return this;
    }

    public CrudFunctionsBuilder<E> updater(BiFunction<E, Criteria, Integer> updater) {
        crudFunctions.setUpdater(updater);
        return this;
    }

    public CrudFunctionsBuilder<E> partialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
        crudFunctions.setPartialUpdater(partialUpdater);
        return this;
    }

    public CrudFunctionsBuilder<E> deleter(Function<Criteria, Integer> deleter) {
        crudFunctions.setDeleter(deleter);
        return this;
    }

    public CrudFunctionsBuilder<E> batchDeleteEnabled(boolean batchDeleteEnabled) {
        crudFunctions.setBatchDeleteEnabled(batchDeleteEnabled);
        return this;
    }

    public CrudFunctionsBuilder<E> batchUpdateEnabled(boolean batchUpdateEnabled) {
        crudFunctions.setBatchUpdateEnabled(batchUpdateEnabled);
        return this;
    }

    public CrudFunctions build() {
        return crudFunctions;
    }
}
