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

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ChainedJdbcTemplateContext {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private SqlBuilder sqlBuilder;
    private SqlPrinter sqlPrinter;
    private ColumnMapRowMapper columnMapRowMapper;


    public ChainedJdbcTemplateContext() {
        this.setColumnMapRowMapper(new LowerCasedColumnMapRowMapper());
    }

    public ChainedJdbcTemplateContext(DataSource dataSource, JdbcTemplate jdbcTmpl,
                                      SqlBuilder sqlBuilder, SqlPrinter sqlPrinter) {
        this();
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTmpl;
        this.sqlBuilder = sqlBuilder;
        this.sqlPrinter = sqlPrinter;

    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SqlBuilder getSqlBuilder() {
        return sqlBuilder;
    }

    public void setSqlBuilder(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    public SqlPrinter getSqlPrinter() {
        return sqlPrinter;
    }

    public void setSqlPrinter(SqlPrinter sqlPrinter) {
        this.sqlPrinter = sqlPrinter;
    }

    public ColumnMapRowMapper getColumnMapRowMapper() {
        return columnMapRowMapper;
    }

    public void setColumnMapRowMapper(ColumnMapRowMapper columnMapRowMapper) {
        this.columnMapRowMapper = columnMapRowMapper;
    }
}
