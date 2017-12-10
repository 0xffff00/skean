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

package party.threebody.skean.data.query;

import party.threebody.skean.misc.SkeanInvalidArgumentException;

import java.util.Collection;

public class CriteriaAndSortingAndPaging extends Criteria {

    private SortingField[] sortingField;
    private PagingInfo pagingInfo;


    public static CriteriaAndSortingAndPaging EMPTY =
            new CriteriaAndSortingAndPaging(null, null, null);

    public CriteriaAndSortingAndPaging() {
    }

    public CriteriaAndSortingAndPaging(Criterion[] criteria, SortingField[] sortingField, PagingInfo pagingInfo) {
        setCriteria(criteria);
        this.sortingField = sortingField;
        this.pagingInfo = pagingInfo;
    }

    public SortingField[] getSortingField() {
        return sortingField;
    }

    public void setSortingField(SortingField[] sortingField) {
        this.sortingField = sortingField;
    }

    public PagingInfo getPagingInfo() {
        return pagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        this.pagingInfo = pagingInfo;
    }

    @Override
    public void ensureAllNamesLegal(Collection<String> whiteList) {
        super.ensureAllNamesLegal(whiteList);
        if (sortingField == null) {
            return;
        }
        for (int i = 0; i < sortingField.length; ++i) {
            String name = sortingField[i].getName();
            if (!whiteList.contains(name)) {
                throw new SkeanInvalidArgumentException("illegal field name: [" + name + "]");
            }
        }
    }

}
