package party.threebody.skean.jdbc;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import party.threebody.skean.jdbc.phrase.SqlBuilder;

public class ChainedJdbcTemplateContext {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SqlBuilder sqlBuilder;
	private SqlPrinter sqlPrinter;
	private ColumnMapRowMapper columnMapRowMapper;
	private boolean printSqlAndResult;
	private int maxCharsToPrintSqlResult;
	private int maxCharsToPrintInOneLine;

	public ChainedJdbcTemplateContext() {
		this.setColumnMapRowMapper(new LowerCasedColumnMapRowMapper());
		this.setMaxCharsToPrintSqlResult(1024);
		this.setMaxCharsToPrintInOneLine(200);
		this.setPrintSqlAndResult(false);
		this.setSqlPrinter(new SqlPrinter(this));
	}

	public ChainedJdbcTemplateContext(DataSource dataSource, JdbcTemplate jdbcTmpl, SqlBuilder sqlBuilder) {
		this();
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTmpl;
		this.sqlBuilder = sqlBuilder;

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

	public void setJdbcTemplate(JdbcTemplate jdbcTmpl) {
		this.jdbcTemplate = jdbcTmpl;
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

	public int getMaxCharsToPrintSqlResult() {
		return maxCharsToPrintSqlResult;
	}

	public void setMaxCharsToPrintSqlResult(int maxCharsToPrintSqlResult) {
		this.maxCharsToPrintSqlResult = maxCharsToPrintSqlResult;
	}

	public int getMaxCharsToPrintInOneLine() {
		return maxCharsToPrintInOneLine;
	}

	public void setMaxCharsToPrintInOneLine(int maxCharsToPrintInOneLine) {
		this.maxCharsToPrintInOneLine = maxCharsToPrintInOneLine;
	}

	public boolean isPrintSqlAndResult() {
		return printSqlAndResult;
	}

	public void setPrintSqlAndResult(boolean printSqlAndResult) {
		this.printSqlAndResult = printSqlAndResult;
	}

	public SqlPrinter getSqlPrinter() {
		return sqlPrinter;
	}

	public void setSqlPrinter(SqlPrinter sqlPrinter) {
		this.sqlPrinter = sqlPrinter;
	}

}
