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

import org.springframework.jdbc.core.JdbcTemplate;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.phrase.SelectPhrase;
import party.threebody.skean.jdbc.phrase.SqlPhrase;

import javax.sql.DataSource;

public class ChainedJdbcTemplate {

    private ChainedJdbcTemplateContext context;

    public ChainedJdbcTemplate(DataSource dataSource, JdbcTemplate jdbcTmpl,
                               SqlBuilder sqlBuilder, SqlPrinter sqlPrinter) {
        this.context = new ChainedJdbcTemplateContext(dataSource, jdbcTmpl, sqlBuilder, sqlPrinter);
    }

    public ChainedJdbcTemplate(ChainedJdbcTemplateContext context) {
        this.context = context;
    }

    public FromPhrase from(String table) {
        return new FromPhrase(context, table, null);
    }

    public SelectPhrase fromSql(String originalSql) {
        return new FromPhrase(context, null, originalSql).select();
    }

    public SqlPhrase sql(String sql) {
        return new SqlPhrase(context, sql);
    }


    public ChainedJdbcTemplateContext getContext() {
        return context;
    }

    public void setContext(ChainedJdbcTemplateContext context) {
        this.context = context;
    }
}