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

import party.threebody.skean.lang.Beans;
import party.threebody.skean.misc.SkeanNotImplementedException;
import party.threebody.skean.web.mvc.dao.AbstractCrudDAO;
import party.threebody.skean.web.mvc.dao.MultiPKsCrudDAO;
import party.threebody.skean.web.mvc.dao.SinglePKCrudDAO;

public class CrudFunctionsUtils {

    protected static <O> O raiseNotImplementedWithMsg(String msg) {
        throw new SkeanNotImplementedException("Not Implemented Yet: " + msg);
    }

    public static <E> void fillFromCrudDAO(CrudFunctions<E> funcs, AbstractCrudDAO<E> dao) {
        funcs.setListReader(dao::readList);
        funcs.setCountReader(dao::readCount);
        funcs.setOneCreator(dao::create);
        funcs.setUpdater(dao::updateSome);
        funcs.setPartialUpdater(dao::partialUpdateSome);
        funcs.setDeleter(dao::deleteSome);
    }

    public static <E, PK> void fillFromSinglePKCrudDAO(SinglePKCrudFunctions<E, PK> funcs, SinglePKCrudDAO<E, PK> dao) {
        fillFromCrudDAO(funcs, dao);
        funcs.setOneReader(dao::readOne);
        funcs.setOneUpdater(dao::update);
        funcs.setOnePartialUpdater(dao::partialUpdate);
        funcs.setOneDeleter(dao::delete);
        funcs.setPkNameSupplier(dao::getPrimaryKeyColumn);
        funcs.setPkGetter(e -> (PK) Beans.getProperty(e, funcs.getPkNameSupplier().get()));
        //sfuncs.resetOneCreatorWithReturn();
    }

    public static <E> void fillFromMultiPKsCrudDAO(MultiPKsCrudFunctions<E> funcs, MultiPKsCrudDAO<E> dao) {
        fillFromCrudDAO(funcs, dao);
        funcs.setOneReader(dao::readOne);
        funcs.setOneUpdater(dao::update);
        funcs.setOnePartialUpdater(dao::partialUpdate);
        funcs.setOneDeleter(dao::delete);
        funcs.setPkNamesSupplier(dao::getPrimaryKeyColumns);
        funcs.setPkGetter(e -> Beans.getProperties(e, funcs.getPkNamesSupplier().get()));
        funcs.resetOneCreatorWithReturn();
    }


}
