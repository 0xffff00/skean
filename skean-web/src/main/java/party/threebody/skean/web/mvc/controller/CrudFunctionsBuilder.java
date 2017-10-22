package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CrudFunctionsBuilder<E, PK> {
    SimpleCrudFunctions<E, PK> funcs;

    public CrudFunctionsBuilder() {
        funcs = new SimpleCrudFunctions<>();
        funcs.pkGetter = SimpleCrudFunctions::raiseNotImplemented;
        funcs.listReader = SimpleCrudFunctions::raiseNotImplemented;
        funcs.countReader = SimpleCrudFunctions::raiseNotImplemented;
        funcs.oneReader = SimpleCrudFunctions::raiseNotImplemented;
        funcs.creator = SimpleCrudFunctions::raiseNotImplemented;
        funcs.creatorWithReturn = e -> {
            funcs.creator.apply(e);
            PK pk = funcs.pkGetter.apply(e);
            return funcs.oneReader.apply(pk);
        };
        funcs.entireUpdater = SimpleCrudFunctions::raiseNotImplemented;
        funcs.partialUpdater = SimpleCrudFunctions::raiseNotImplemented;
        funcs.deleter = SimpleCrudFunctions::raiseNotImplemented;
    }


    public SimpleCrudFunctions<E, PK> build() {
        return funcs;
    }

    public CrudFunctionsBuilder<E, PK> pkGetter(Function<E, PK> pkGetter) {
        funcs.pkGetter = pkGetter;
        return this;
    }


    public CrudFunctionsBuilder<E, PK> listReader(Function<QueryParamsSuite, List<E>> listReader) {
        funcs.listReader = listReader;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> countReader(Function<QueryParamsSuite, Integer> countReader) {
        funcs.countReader = countReader;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> oneReader(Function<PK, E> oneReader) {
        funcs.oneReader = oneReader;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> creator(Function<E, Integer> creator) {
        funcs.creator = creator;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> creatorWithReturn(Function<E, E> creator) {
        funcs.creatorWithReturn = creator;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> entireUpdater(BiFunction<E, PK, Integer> entireUpdater) {
        funcs.entireUpdater = entireUpdater;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> partialUpdater(BiFunction<Map<String, Object>, PK, Integer> partialUpdater) {
        funcs.partialUpdater = partialUpdater;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> deleter(Function<PK, Integer> deleter) {
        funcs.deleter = deleter;
        return this;
    }


    public CrudFunctionsBuilder<E, PK> fromDAO(SinglePKJpaCrudDAO<E, PK> dao) {
        funcs.pkGetter = e -> (PK) dao.convertEntityBeanToMap(e).get(dao.getPrimaryKeyColumns().get(0));
        funcs.listReader = dao::readList;
        funcs.countReader = dao::readCount;
        funcs.oneReader = dao::readOne;
        funcs.creator = dao::create;
        funcs.deleter = dao::delete;
        return this;
    }
}
