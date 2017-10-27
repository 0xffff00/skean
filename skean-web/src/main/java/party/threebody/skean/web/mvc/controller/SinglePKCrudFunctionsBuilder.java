/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.web.mvc.controller;

import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SinglePKCrudFunctionsBuilder<E, PK> {
    private SinglePKCrudFunctions<E, PK> singlePKCrudFunctions;

    protected SinglePKCrudFunctionsBuilder() {
        singlePKCrudFunctions = new SinglePKCrudFunctions<>();
    }

    public SinglePKCrudFunctionsBuilder<E, PK> pkNameSupplier(String pkName) {
        singlePKCrudFunctions.setPkNameSupplier(() -> pkName);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> pkNameSupplier(Supplier<String> pkNameSupplier) {
        singlePKCrudFunctions.setPkNameSupplier(pkNameSupplier);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> listReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
        singlePKCrudFunctions.setListReader(listReader);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> pkGetter(Function<E, PK> pkGetter) {
        singlePKCrudFunctions.setPkGetter(pkGetter);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> countReader(Function<Criteria, Integer> countReader) {
        singlePKCrudFunctions.setCountReader(countReader);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> oneReader(Function<PK, E> oneReader) {
        singlePKCrudFunctions.setOneReader(oneReader);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> oneCreatorWithReturn(Function<E, E> oneCreatorWithReturn) {
        singlePKCrudFunctions.setOneCreatorWithReturn(oneCreatorWithReturn);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> oneCreator(Function<E, Integer> oneCreator) {
        singlePKCrudFunctions.setOneCreator(oneCreator);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> oneUpdater(BiFunction<E, PK, Integer> oneUpdater) {
        singlePKCrudFunctions.setOneUpdater(oneUpdater);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> updater(BiFunction<E, Criteria, Integer> updater) {
        singlePKCrudFunctions.setUpdater(updater);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> onePartialUpdater(BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater) {
        singlePKCrudFunctions.setOnePartialUpdater(onePartialUpdater);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> partialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
        singlePKCrudFunctions.setPartialUpdater(partialUpdater);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> oneDeleter(Function<PK, Integer> oneDeleter) {
        singlePKCrudFunctions.setOneDeleter(oneDeleter);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> deleter(Function<Criteria, Integer> deleter) {
        singlePKCrudFunctions.setDeleter(deleter);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> batchDeleteEnabled(boolean batchDeleteEnabled) {
        singlePKCrudFunctions.setBatchDeleteEnabled(batchDeleteEnabled);
        return this;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> batchUpdateEnabled(boolean batchUpdateEnabled) {
        singlePKCrudFunctions.setBatchUpdateEnabled(batchUpdateEnabled);
        return this;
    }

    public SinglePKCrudFunctions build() {

        return singlePKCrudFunctions;
    }

    public SinglePKCrudFunctionsBuilder<E, PK> fromSinglePKCrudDAO(SinglePKCrudDAO<E, PK> dao) {
        CrudFunctionsUtils.fillFromSinglePKCrudDAO(singlePKCrudFunctions, dao);
        return this;
    }
}
