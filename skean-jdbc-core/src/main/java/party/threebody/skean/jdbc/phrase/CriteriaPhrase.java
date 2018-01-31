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

import org.springframework.jdbc.core.RowMapper;

import party.threebody.skean.data.query.SortingField;

public class CriteriaPhrase implements Phrase {

	private FromPhrase root;

	public CriteriaPhrase(FromPhrase root) {
		this.root = root;
	}

	// ------ sorting --------
	public OrderByPhrase orderBy(String... cols) {
		return root.orderBy(cols);
	}
	public OrderByPhrase orderBy(SortingField[] cols) {
		return root.orderBy(cols);
	}
	// ------ paging --------
	public PagePhrase page(int page, int size) {
		return root.page(page, size);
	}

	public PagePhrase limit(int limit) {
		return root.limit(limit);
	}

	public PagePhrase offset(int offset) {
		return root.offset(offset);
	}

	// ------ fetching --------
	public List<Map<String, Object>> list() {
		return root.list();
	}

	public <T> List<T> list(Class<T> elementType) {
		return root.list(elementType);
	}

	public <T> List<T> list(RowMapper<T> rowMapper) {
		return root.list(rowMapper);
	}
	
	public <T> List<T> listOfSingleCol(Class<T> columnType){
		return root.listOfSingleCol(columnType);
	}

	public Map<String, Object> first() {
		return root.first();
	}

	public <T> T first(Class<T> elementType) {
		return root.first(elementType);
	}

	public <T> T first(RowMapper<T> rowMapper) {
		return root.first(rowMapper);
	}

	public Object firstCell() {
		return root.firstCell();
	}

	public <T> T single(Class<T> elementType) {
		return root.single(elementType);
	}

	public int count() {
		return root.count();
	}

	// ------ modifying --------

	public int update() {
		return root.update();
	}

	public int delete() {
		return root.delete();
	}
}
