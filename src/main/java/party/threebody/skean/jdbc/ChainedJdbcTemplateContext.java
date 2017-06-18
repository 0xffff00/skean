package party.threebody.skean.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import party.threebody.skean.jdbc.phrase.SqlBuilder;

public class ChainedJdbcTemplateContext {

	private DataSource dataSource;
	private JdbcTemplate jdbcTmpl;
	private NamedParameterJdbcTemplate npJdbcTmpl;
	private SqlBuilder sqlBuilder;
	private ColumnMapRowMapper columnMapRowMapper;

	
	public ChainedJdbcTemplateContext() {
		this.setColumnMapRowMapper(new ColumnMapRowMapper());
	}

	public ChainedJdbcTemplateContext(DataSource dataSource, JdbcTemplate jdbcTmpl, NamedParameterJdbcTemplate npJdbcTmpl,
			 SqlBuilder sqlBuilder) {
		this();
		this.dataSource = dataSource;
		this.jdbcTmpl = jdbcTmpl;
		this.npJdbcTmpl = npJdbcTmpl;
		this.sqlBuilder = sqlBuilder;
		
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JdbcTemplate getJdbcTmpl() {
		return jdbcTmpl;
	}

	public void setJdbcTmpl(JdbcTemplate jdbcTmpl) {
		this.jdbcTmpl = jdbcTmpl;
	}

	public NamedParameterJdbcTemplate getNpJdbcTmpl() {
		return npJdbcTmpl;
	}

	public void setNpJdbcTmpl(NamedParameterJdbcTemplate npJdbcTmpl) {
		this.npJdbcTmpl = npJdbcTmpl;
	}


	public ColumnMapRowMapper getColumnMapRowMapper() {
		return columnMapRowMapper;
	}

	public void setColumnMapRowMapper(ColumnMapRowMapper columnMapRowMapper) {
		this.columnMapRowMapper = columnMapRowMapper;
	}

	public SqlBuilder getSqlBuilder() {
		return sqlBuilder;
	}

	public void setSqlBuilder(SqlBuilder sqlBuilder) {
		this.sqlBuilder = sqlBuilder;
	}

}
