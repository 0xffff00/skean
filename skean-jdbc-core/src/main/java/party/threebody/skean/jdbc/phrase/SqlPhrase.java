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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;

import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.util.SqlAndArgs;
import party.threebody.skean.lang.Beans;

public class SqlPhrase extends DefaultRootPhrase {

	static Logger logger = LoggerFactory.getLogger(SqlPhrase.class);

	String sql;

	Object argObj;
	Map<String, Object> argMap;
	Object[] args;

	public SqlPhrase(ChainedJdbcTemplateContext context, String sql) {
		super();
		this.context = context;
		this.sql = sql;
	}


	// ------ args value filling --------
	public ArgPhrase argArr(Object[] vals) {
		this.args = vals;
		return new ArgPhrase(this);
	}

	public ArgPhrase argMap(Map<String, Object> vals) {
		this.argMap = vals;
		return new ArgPhrase(this);
	}

	public ArgPhrase argObj(Object vals) {
		this.argObj = vals;
		return new ArgPhrase(this);
	}

	@SuppressWarnings("unchecked")
	public ArgPhrase arg(Object... val) {
		if (val == null || val.length == 0) {
			return new ArgPhrase(this);
		}
		if (val.length == 1) {
			Object v0 = val[0];
			if (v0 == null) {
				argMap(Collections.emptyMap());
			}
			if (v0 instanceof Map) {
				argMap((Map<String, Object>) v0);
			}

			if (v0 instanceof Collection) {
				argArr(val);
			}
			if (Beans.instanceOfSimpleType(v0)) {
				argArr(val);
			}
			argObj(val);
		}
		return argArr(val);
	}

	// ------ fetching --------
	
	@Override
	protected SqlAndArgs buildSelectSqlAndArgs() {
		return new SqlAndArgs(sql,args);
	}
	
	

	// ------ modifying --------

	public int execute() {
		context.getSqlPrinter().printSql(sql, args);
		int rna = context.getJdbcTemplate().update(sql, new ArgumentPreparedStatementSetter(args));
		context.getSqlPrinter().printRowNumAffected(rna);
		return rna;

	}

	

}