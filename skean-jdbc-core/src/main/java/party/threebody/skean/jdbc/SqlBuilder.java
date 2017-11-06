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

package party.threebody.skean.jdbc;

import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.util.SqlAndArgs;
/**
 * SQL Builder interface for ChainedJdbcTemplate's FromPhrase
 * @author hzk
 *
 */
public interface SqlBuilder {

	SqlAndArgs buildSelectSql(FromPhrase p);

	SqlAndArgs buildInsertSql(FromPhrase p);

	SqlAndArgs buildUpdateSql(FromPhrase p);

	SqlAndArgs buildDeleteSql(FromPhrase p);

	void setConfig(SqlBuilderConfig conf);

}
