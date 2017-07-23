package party.threebody.skean.jdbc.util;

import java.util.Arrays;

public class SqlAndArgs {
	private String sql;
	private Object[] args;

	public SqlAndArgs(String sql, Object[] args) {
		super();
		this.sql = sql;
		this.args = args;
	}

	public String getSql() {
		return sql;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return toPlainString();
	}

	public String toPlainString() {
		return sql + "\n>>>>" + Arrays.toString(args);
	}

	public String toAnsiString() {
		return SqlPrintUtils.ansiFormatSql(sql, args);
	}

}
