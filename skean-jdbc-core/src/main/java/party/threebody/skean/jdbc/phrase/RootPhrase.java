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

public interface RootPhrase extends Phrase {
	List<Map<String, Object>> list();

	<T> List<T> list(Class<T> elementType);

	<T> List<T> list(RowMapper<T> rowMapper);

	Map<String, Object> first();

	<T> T first(Class<T> elementType);

	<T> T first(RowMapper<T> rowMapper);

	Object firstCell();

	int count();
}
