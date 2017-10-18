package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.web.SkeanNotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CrudFunctionsBuilder<E, PK> {
    SimpleCrudFunctions<E, PK> funcs;

    public CrudFunctionsBuilder() {
        funcs = new SimpleCrudFunctions<>();
        funcs.pkGetter = this::raiseNotImplemented;
        funcs.listReader = this::raiseNotImplemented;
        funcs.countReader = this::raiseNotImplemented;
        funcs.oneReader = this::raiseNotImplemented;
        funcs.creator = this::raiseNotImplemented;
        funcs.entireUpdater = this::raiseNotImplemented;
        funcs.partialUpdater = this::raiseNotImplemented;
        funcs.deleter = this::raiseNotImplemented;
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

    public CrudFunctionsBuilder<E, PK> creator(Function<E, E> creator) {
        funcs.creator = creator;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> entireUpdater(BiFunction<E, PK, Integer> entireUpdater) {
        funcs.entireUpdater = entireUpdater;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> partialUpdater(BiFunction<Map<String,Object>,PK, Integer> partialUpdater) {
        funcs.partialUpdater = partialUpdater;
        return this;
    }

    public CrudFunctionsBuilder<E, PK> deleter(Function<PK, Integer> deleter) {
        funcs.deleter = deleter;
        return this;
    }

    private <O> O raiseNotImplemented() {
        throw new SkeanNotImplementedException();
    }

    private <I,O> O raiseNotImplemented(I i) {
        throw new SkeanNotImplementedException();
    }
    private <I1,I2,O> O raiseNotImplemented(I1 i1,I2 i2) {
        throw new SkeanNotImplementedException();
    }
    private <I1,I2,I3,O> O raiseNotImplemented(I1 i1,I2 i2,I3 i3) {
        throw new SkeanNotImplementedException();
    }


}
