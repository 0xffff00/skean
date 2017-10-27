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

public class BasicCriterion implements Criterion {

	private String name;
	private String operator;
	private Object value;

	public BasicCriterion() {
		this(null, null, null);
	}

	public BasicCriterion(String name, Object value) {
		this(name, null, value);
	}

	public BasicCriterion(String name, String operator, Object value) {
		this.name = name;
		this.operator = operator;
		this.value = value;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getOperator() {
		return operator;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "SimpleCriterionImpl{name:" + name + ", operator:" + operator + ", value:" + value + "}";
	}

}