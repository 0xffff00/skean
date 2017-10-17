package party.threebody.skean.web.testapp.mvc.controller;

import party.threebody.skean.data.query.QueryParamsSuite;
import party.threebody.skean.lang.TriFunction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleCrudFunctions<E, PK> {

    protected Function<E, PK> pkGetter;
    protected Function<QueryParamsSuite, List<E>> listReader;
    protected Function<QueryParamsSuite, Integer> countReader;
    protected Function<PK, E> oneReader;
    protected Function<E, E> creator;
    protected BiFunction<E, PK, Integer> entireUpdater;
    protected BiFunction<Map<String,Object>,PK, Integer> partialUpdater;
    protected Function<PK, Integer> deleter;


    public void setPkGetter(Function<E, PK> pkGetter) {
        this.pkGetter = pkGetter;
    }

    public void setListReader(Function<QueryParamsSuite, List<E>> listReader) {
        this.listReader = listReader;
    }

    public void setCountReader(Function<QueryParamsSuite, Integer> countReader) {
        this.countReader = countReader;
    }

    public void setOneReader(Function<PK, E> oneReader) {
        this.oneReader = oneReader;
    }

    public void setCreator(Function<E, E> creator) {
        this.creator = creator;
    }

    public void setEntireUpdater(BiFunction<E, PK, Integer> entireUpdater) {
        this.entireUpdater = entireUpdater;
    }

    public void setPartialUpdater(BiFunction<Map<String,Object>,PK, Integer> partialUpdater) {
        this.partialUpdater = partialUpdater;
    }

    public void setDeleter(Function<PK, Integer> deleter) {
        this.deleter = deleter;
    }

    public Function<E, PK> getPkGetter() {
        return pkGetter;
    }

    public Function<QueryParamsSuite, List<E>> getListReader() {
        return listReader;
    }

    public Function<QueryParamsSuite, Integer> getCountReader() {
        return countReader;
    }

    public Function<PK, E> getOneReader() {
        return oneReader;
    }

    public Function<E, E> getCreator() {
        return creator;
    }

    public BiFunction<E, PK, Integer> getEntireUpdater() {
        return entireUpdater;
    }

    public BiFunction<Map<String,Object>,PK, Integer>  getPartialUpdater() {
        return partialUpdater;
    }

    public Function<PK, Integer> getDeleter() {
        return deleter;
    }
}

