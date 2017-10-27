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

import java.util.List;
import java.util.Map;

import party.threebody.skean.data.query.BasicCriterion;
import party.threebody.skean.data.query.Criteria;

/**
 * a 'val()' phrase after 'affect()' phrase.
 * 
 * @author hzk
 * @since 2017-06-18
 */
public class AffectValPhrase implements Phrase {

	private FromPhrase root;

	AffectValPhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ filtering --------
	public ByPhrase by(String... cols) {
		return root.by(cols);
	}

	public ByPhrase by(List<String> cols){
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
	public CriteriaPhrase criteria(Criteria criteria) {
		return root.criteria(criteria);
	}

	// ------ modifying --------
	public int insert() {
		return root.insert();
	}

	public int delete() {
		return root.delete();
	}

}
