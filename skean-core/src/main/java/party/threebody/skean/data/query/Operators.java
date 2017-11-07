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

public interface Operators {
    String LT = "<";
    String GT = ">";
    String LE = "<=";
    String GE = ">=";
    String EQ = "=";
    String NE = "!=";
    String K = "like";
    String NK = "unlike";
    String KL = "likeLeft";
    String KR = "likeRight";
    String NKL = "notLikeLeft";
    String NKR = "notLikeRight";
    String IN = "in";
    String NIN = "notIn";
}

