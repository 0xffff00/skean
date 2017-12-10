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
 * expression looks like CSS3 style.
 */
public enum Operator {
    EQ("equals", "="),
    LT("lessThan", "<"),
    GT("greaterThan", ">"),
    NE("notEqual", "!="),
    LE("lessThanOrEqual", "<="),
    GE("greaterThanOrEqual", ">="),
    K("like", "~="),
    NK("notLike", "!~="),
    KL("likeLeft", "^="),
    KR("likeRight", "$="),
    NKL("notLikeLeft", "!^="),
    NKR("notLikeRight", "!$="),
    IN("in"),
    NIN("motIn"),
    IS("is"),
    ISNOT("isNot");


    private String fullName;
    private String expression;

    Operator(String name, String expr) {
        this.fullName = name;
        this.expression = expr;
    }

    Operator(String name) {
        this.fullName = name;
        this.expression = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }
}
