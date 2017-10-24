package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.jdbc.util.JavaBeans;
import party.threebody.skean.web.mvc.dao.AbstractCrudDAO;
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SinglePKCrudFunctions<E, PK> extends CrudFunctions<E> {


    protected String pkName;
    protected Function<E, PK> pkGetter;
    protected Function<PK, E> oneReader;
    protected Function<E, E> oneCreatorWithReturn;
    protected BiFunction<E, PK, Integer> oneUpdater;
    protected BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater;
    protected Function<PK, Integer> oneDeleter;

    protected SinglePKCrudFunctions() {
        super();
        oneReader = SinglePKCrudFunctions::raiseNotImplemented;
        oneCreator = SinglePKCrudFunctions::raiseNotImplemented;
        oneUpdater = SinglePKCrudFunctions::raiseNotImplemented;
        oneDeleter = SinglePKCrudFunctions::raiseNotImplemented;
        onePartialUpdater = SinglePKCrudFunctions::raiseNotImplemented;
        pkGetter = e -> (PK) JavaBeans.getProperty(e, pkName);
        oneCreatorWithReturn = e -> {
            oneCreator.apply(e);
            PK pk = pkGetter.apply(e);
            return oneReader.apply(pk);
        };


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

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public Function<E, PK> getPkGetter() {
        return pkGetter;
    }

    public void setPkGetter(Function<E, PK> pkGetter) {
        this.pkGetter = pkGetter;
    }

    public static class Builder<E, PK> extends CrudFunctions.Builder<E> {
        SinglePKCrudFunctions<E, PK> funcs;

        public Builder() {
            funcs = new SinglePKCrudFunctions<>();
        }

        public SinglePKCrudFunctions<E, PK> build() {
            return funcs;
        }

        // --------------from Super Builder---------------
        public Builder<E, PK> listReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
            funcs.listReader = listReader;
            return this;
        }

        public Builder<E, PK> countReader(Function<Criteria, Integer> countReader) {
            funcs.countReader = countReader;
            return this;
        }

        public Builder<E, PK> oneCreator(Function<E, Integer> creator) {
            funcs.oneCreator = creator;
            return this;
        }

        public Builder<E, PK> entireUpdater(BiFunction<E, Criteria, Integer> entireUpdater) {
            funcs.updater = entireUpdater;
            return this;
        }

        public Builder<E, PK> partialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
            funcs.partialUpdater = partialUpdater;
            return this;
        }

        public Builder<E, PK> deleter(Function<Criteria, Integer> deleter) {
            funcs.deleter = deleter;
            return this;
        }

        public Builder<E, PK> fromCrudDAO(AbstractCrudDAO<E> dao) {
            super.fromCrudDAO(dao);
            return this;
        }


        // ---------------from local---------------
        public Builder<E, PK> oneCreatorWithReturn(Function<E, E> oneCreatorWithReturn) {
            funcs.oneCreatorWithReturn = oneCreatorWithReturn;
            return this;
        }

        public Builder<E, PK> oneUpdater(BiFunction<E, PK, Integer> oneUpdater) {
            funcs.oneUpdater = oneUpdater;
            return this;
        }

        public Builder<E, PK> oneReader(Function<PK, E> oneReader) {
            funcs.oneReader = oneReader;
            return this;
        }

        public Builder<E, PK> onePartialUpdater(BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater) {
            funcs.onePartialUpdater = onePartialUpdater;
            return this;
        }

        public Builder<E, PK> oneDeleter(Function<PK, Integer> oneDeleter) {
            funcs.oneDeleter = oneDeleter;
            return this;
        }

        public Builder<E, PK> pkName(String pkName) {
            funcs.pkName = pkName;
            return this;
        }

        public Builder<E, PK> fromSinglePKJpaCrudDAO(SinglePKJpaCrudDAO<E, PK> dao) {
            fromCrudDAO(dao);
            //funcs.pkGetter = e -> (PK) dao.convertEntityBeanToMap(e).get(dao.getPrimaryKeyColumns().get(0));
            funcs.pkName = dao.getPrimaryKeyColumn();
            funcs.oneReader = dao::readOne;
            funcs.oneUpdater = dao::update;
            funcs.onePartialUpdater = dao::partialUpdate;
            funcs.oneDeleter = dao::delete;
            return this;
        }
    }

}

