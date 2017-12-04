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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @since 2.1
 */
public class Criteria {

    public static final String NON_EXIST_COL_NAME = "51892";   //just a random number
    private static final List<String> SKEAN_OBJ_NAME_WHITELIST = Arrays.asList(NON_EXIST_COL_NAME);
    private Criterion[] criteria;

    /**
     * allow all, deny nothing
     */
    public static final Criteria ALLOW_ALL = new Criteria();

    /**
     * allow none, deny all
     */
    public static final Criteria DENY_ALL = new Criteria(new Criterion[]{Criterion.FALSE});

    protected Criteria() {

    }

    protected Criteria(Criterion[] criteria) {
        this.criteria = criteria;
    }

    public Criterion[] getCriteria() {
        return criteria;
    }

    public static Criteria of(Criterion... arr) {
        return new Criteria(arr);
    }

    public void setCriteria(Criterion[] criteria) {
        this.criteria = criteria;
    }

    public void ensureAllNamesLegal(Collection<String> whiteList) {
        List<String> finalWhiteList = new ArrayList(SKEAN_OBJ_NAME_WHITELIST);
        finalWhiteList.addAll(whiteList);
        if (criteria == null) {
            return;
        }
        for (int i = 0; i < criteria.length; ++i) {
            String name = criteria[i].getName();
            if (!finalWhiteList.contains(name)) {
                throw new SkeanInvalidArgumentException("illegal field name: [" + name + "]");
            }
        }
    }
}
