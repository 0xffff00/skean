package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.SkeanNotImplementedException;
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

    protected Function<CriteriaAndSortingAndPaging, List<E>> listReader;
    protected Function<Criteria, Integer> countReader;
    protected Function<E, Integer> oneCreator;
    protected BiFunction<E, Criteria, Integer> updater;
    protected BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater;
    protected Function<Criteria, Integer> deleter;
    protected boolean batchDeleteEnabled;
    protected boolean batchUpdateEnabled;

    protected CrudFunctions() {
        listReader = SinglePKCrudFunctions::raiseNotImplemented;
        countReader = SinglePKCrudFunctions::raiseNotImplemented;
        oneCreator = SinglePKCrudFunctions::raiseNotImplemented;
        updater = SinglePKCrudFunctions::raiseNotImplemented;
        partialUpdater = SinglePKCrudFunctions::raiseNotImplemented;
        deleter = SinglePKCrudFunctions::raiseNotImplemented;
        batchDeleteEnabled = false;
        batchUpdateEnabled = false;
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

    protected static <O> O raiseNotImplemented() {
        throw new SkeanNotImplementedException();
    }

    protected static <I, O> O raiseNotImplemented(I i) {
        throw new SkeanNotImplementedException();
    }

    protected static <I1, I2, O> O raiseNotImplemented(I1 i1, I2 i2) {
        throw new SkeanNotImplementedException();
    }

    protected static <I1, I2, I3, O> O raiseNotImplemented(I1 i1, I2 i2, I3 i3) {
        throw new SkeanNotImplementedException();
    }

    public static class Builder<E> {
        CrudFunctions<E> funcs;

        public Builder() {
            funcs = new CrudFunctions<>();
        }


        public CrudFunctions<E> build() {
            return funcs;
        }


        public Builder<E> listReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
            funcs.listReader = listReader;
            return this;
        }

        public Builder<E> countReader(Function<Criteria, Integer> countReader) {
            funcs.countReader = countReader;
            return this;
        }

        public Builder<E> oneCreator(Function<E, Integer> creator) {
            funcs.oneCreator = creator;
            return this;
        }

        public Builder<E> entireUpdater(BiFunction<E, Criteria, Integer> entireUpdater) {
            funcs.updater = entireUpdater;
            return this;
        }

        public Builder<E> partialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
            funcs.partialUpdater = partialUpdater;
            return this;
        }

        public Builder<E> deleter(Function<Criteria, Integer> deleter) {
            funcs.deleter = deleter;
            return this;
        }

        public Builder<E> fromCrudDAO(AbstractCrudDAO<E> dao) {
            funcs.listReader = dao::readList;
            funcs.countReader = dao::readCount;
            funcs.oneCreator = dao::create;
            funcs.updater = dao::updateSome;
            funcs.partialUpdater = dao::partialUpdateSome;
            funcs.deleter = dao::deleteSome;
            return this;
        }

    }
}
