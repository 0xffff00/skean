package party.threebody.skean.jdbc.phrase;

public interface SqlBuilder {

	SqlAndArgs buildSelectSql(FromPhrase p);

	SqlAndArgs buildInsertSql(FromPhrase p);

	SqlAndArgs buildUpdateSql(FromPhrase p);

	SqlAndArgs buildDeleteSql(FromPhrase p);

	void setConfig(SqlBuilderConfig conf);

}
