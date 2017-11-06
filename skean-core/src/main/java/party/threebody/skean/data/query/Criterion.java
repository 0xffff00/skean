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

/**
 * 通用查询条件准则<br>
 *
 * @author hzk
 * @since 2017-06-17
 */
public interface Criterion {

    public static final Criterion NONE = new BasicCriterion("1", "0");

    void setName(String name);

    String getName();

    void setValue(Object value);

    Object getValue();
}