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

package party.threebody.skean.jdbc.rs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = TripleColsBeanSerializer.class)
public class TripleColsBean<F0, F1, F2> {
    protected F0 f0;
    protected F1 f1;
    protected F2 f2;

    public TripleColsBean(F0 f0, F1 f1, F2 f2) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
    }

    public F0 get0() {
        return f0;
    }

    public F1 get1() {
        return f1;
    }

    public F2 get2() {
        return f2;
    }

    @Override
    public String toString() {
        return "(" + f0 + "," + f1 + "," + f2 + ")";
    }
}
