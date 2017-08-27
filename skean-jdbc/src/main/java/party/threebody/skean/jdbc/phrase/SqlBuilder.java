package party.threebody.skean.jdbc.phrase;

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
