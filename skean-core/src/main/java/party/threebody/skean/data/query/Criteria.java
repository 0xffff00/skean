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

/**
 * @since 2.1
 */
public class Criteria {

    private Criterion[] criteria;

    protected Criteria() {

    }

    public Criteria(Criterion[] criteria) {
        this.criteria = criteria;
    }

    public Criterion[] getCriteria() {
        return criteria;
    }

    public void setCriteria(Criterion[] criteria) {
        this.criteria = criteria;
    }

    public void ensureAllNamesLegal(Collection<String> whiteList) {
        if (criteria == null) {
            return;
        }
        for (int i = 0; i < criteria.length; ++i) {
            String name = criteria[i].getName();
            if (!whiteList.contains(name)) {
                throw new SkeanInvalidArgumentException("illegal field name: [" + name + "]");
            }
        }
    }
}
