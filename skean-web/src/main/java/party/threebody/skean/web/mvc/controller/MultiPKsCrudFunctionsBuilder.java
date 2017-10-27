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
import party.threebody.skean.web.mvc.dao.MultiPKsCrudDAO;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MultiPKsCrudFunctionsBuilder<E> {
    private MultiPKsCrudFunctions<E> multiPKsCrudFunctions;

    protected MultiPKsCrudFunctionsBuilder() {
        multiPKsCrudFunctions = new MultiPKsCrudFunctions<>();
    }

    public static MultiPKsCrudFunctionsBuilder aMultiPKsCrudFunctions() {
        return new MultiPKsCrudFunctionsBuilder();
    }

    public MultiPKsCrudFunctionsBuilder pkNameSupplier(Supplier<List<String>> pkNameSupplier) {
        multiPKsCrudFunctions.setPkNamesSupplier(pkNameSupplier);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder pkGetter(Function<E, List<Object>> pkGetter) {
        multiPKsCrudFunctions.setPkGetter(pkGetter);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder listReader(Function<CriteriaAndSortingAndPaging, List<E>> listReader) {
        multiPKsCrudFunctions.setListReader(listReader);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder oneReader(Function<List<Object>, E> oneReader) {
        multiPKsCrudFunctions.setOneReader(oneReader);
        multiPKsCrudFunctions.resetOneCreatorWithReturn();
        return this;
    }

    public MultiPKsCrudFunctionsBuilder countReader(Function<Criteria, Integer> countReader) {
        multiPKsCrudFunctions.setCountReader(countReader);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder oneCreatorWithReturn(Function<E, E> oneCreatorWithReturn) {
        multiPKsCrudFunctions.setOneCreatorWithReturn(oneCreatorWithReturn);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder oneCreator(Function<E, Integer> oneCreator) {
        multiPKsCrudFunctions.setOneCreator(oneCreator);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder oneUpdater(BiFunction<E, List<Object>, Integer> oneUpdater) {
        multiPKsCrudFunctions.setOneUpdater(oneUpdater);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder updater(BiFunction<E, Criteria, Integer> updater) {
        multiPKsCrudFunctions.setUpdater(updater);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder onePartialUpdater(BiFunction<Map<String, Object>, List<Object>, Integer> onePartialUpdater) {
        multiPKsCrudFunctions.setOnePartialUpdater(onePartialUpdater);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder partialUpdater(BiFunction<Map<String, Object>, Criteria, Integer> partialUpdater) {
        multiPKsCrudFunctions.setPartialUpdater(partialUpdater);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder oneDeleter(Function<List<Object>, Integer> oneDeleter) {
        multiPKsCrudFunctions.setOneDeleter(oneDeleter);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder deleter(Function<Criteria, Integer> deleter) {
        multiPKsCrudFunctions.setDeleter(deleter);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder batchDeleteEnabled(boolean batchDeleteEnabled) {
        multiPKsCrudFunctions.setBatchDeleteEnabled(batchDeleteEnabled);
        return this;
    }

    public MultiPKsCrudFunctionsBuilder batchUpdateEnabled(boolean batchUpdateEnabled) {
        multiPKsCrudFunctions.setBatchUpdateEnabled(batchUpdateEnabled);
        return this;
    }

    public MultiPKsCrudFunctions build() {
        multiPKsCrudFunctions.setOneCreatorWithReturn(e -> {
            multiPKsCrudFunctions.getOneCreator().apply(e);
            return multiPKsCrudFunctions.getOneReader().apply(multiPKsCrudFunctions.getPkGetter().apply(e));
        });
        return multiPKsCrudFunctions;
    }

    public MultiPKsCrudFunctionsBuilder<E> fromMultiPKsCrudDAO(MultiPKsCrudDAO<E> dao) {

        CrudFunctionsUtils.fillFromMultiPKsCrudDAO(multiPKsCrudFunctions, dao);
        return this;
    }
}
