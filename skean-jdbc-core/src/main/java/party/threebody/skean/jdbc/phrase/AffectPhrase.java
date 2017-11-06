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

package party.threebody.skean.jdbc.phrase;

import java.util.Map;

import party.threebody.skean.data.query.BasicCriterion;

public class AffectPhrase implements Phrase {

	private FromPhrase root;

	AffectPhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ filtering --------
	public ByPhrase by(String... cols) {
		return root.by(cols);
	}

	public ValPhrase by(Map<String, Object> colsNameValMap) {
		return root.by(colsNameValMap);
	}

	public WherePhrase where(String... whereClauses) {
		return root.where(whereClauses);
	}

	public CriteriaPhrase criteria(BasicCriterion[] criteria) {
		return root.criteria(criteria);
	}

	// ------ value(affect) filling --------

	public AffectValPhrase valArr(Object[] vals) {
		return root.afValArr(vals);
	}

	public AffectValPhrase valMap(Map<String, Object> vals) {
		return root.afValMap(vals);
	}

	public AffectValPhrase valObj(Object vals) {
		return root.afValObj(vals);
	}

	public AffectValPhrase val(Object... vals) {
		return root.afVal(vals);
	}

	
}
