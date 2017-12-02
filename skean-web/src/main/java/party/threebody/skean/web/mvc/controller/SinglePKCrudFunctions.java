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

/**
 * @param <E>
 * @param <PK>
 * @since skean 2.1
 */
public class SinglePKCrudFunctions<E, PK> extends CrudFunctions<E> {

    private Supplier<String> pkNameSupplier;
    private Function<E, PK> pkGetter;
    private Function<PK, E> oneReader;
    private Function<E, E> oneCreatorWithReturn;
    private BiFunction<E, PK, Integer> oneUpdater;
    private BiFunction<Map<String, Object>, PK, Integer> onePartialUpdater;
    private Function<PK, Integer> oneDeleter;

    protected SinglePKCrudFunctions() {
        super();
        setPkNameSupplier(()-> CrudFunctionsUtils.raiseNotImplementedWithMsg("pkNamesSupplier"));
        setPkGetter(i1-> CrudFunctionsUtils.raiseNotImplementedWithMsg("pkGetter"));
        setOneReader(i1-> CrudFunctionsUtils.raiseNotImplementedWithMsg("oneReader"));
        setOneUpdater((i1,i2)-> CrudFunctionsUtils.raiseNotImplementedWithMsg("oneUpdater"));
        setOnePartialUpdater((i1,i2)-> CrudFunctionsUtils.raiseNotImplementedWithMsg("onePartialUpdater"));
        setOneDeleter(i1-> CrudFunctionsUtils.raiseNotImplementedWithMsg("oneDeleter"));
        resetOneCreatorWithReturn();

    }

    protected void resetOneCreatorWithReturn(){
        setOneCreatorWithReturn(e -> {
            getOneCreator().apply(e);
            return getOneReader().apply(getPkGetter().apply(e));
        });
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

    public Supplier<String> getPkNameSupplier() {
        return pkNameSupplier;
    }

    public void setPkNameSupplier(Supplier<String> pkNameSupplier) {
        this.pkNameSupplier = pkNameSupplier;
    }

    public Function<E, PK> getPkGetter() {
        return pkGetter;
    }

    public void setPkGetter(Function<E, PK> pkGetter) {
        this.pkGetter = pkGetter;
    }


}

