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
import party.threebody.skean.web.mvc.dao.AbstractCrudDAO;

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

    public CrudFunctionsBuilder<E> fillFromCrudDAO(AbstractCrudDAO<E> dao) {
        CrudFunctionsUtils.fillFromCrudDAO(crudFunctions, dao);
        return this;
    }

    public CrudFunctions build() {
        return crudFunctions;
    }
}
