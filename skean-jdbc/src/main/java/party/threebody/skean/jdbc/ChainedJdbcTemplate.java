package party.threebody.skean.jdbc;

import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.jdbc.phrase.SelectPhrase;
import party.threebody.skean.jdbc.phrase.SqlPhrase;

public class ChainedJdbcTemplate {

    private ChainedJdbcTemplateContext phraseContext;

    public ChainedJdbcTemplate(ChainedJdbcTemplateContext phraseContext) {
        super();
        this.phraseContext = phraseContext;
    }

    public FromPhrase from(String table) {
        return new FromPhrase(phraseContext, table, null);
    }

    public SelectPhrase fromSql(String originalSql) {
        return new FromPhrase(phraseContext, null, originalSql).select();
    }

    public SqlPhrase sql(String sql) {
        return new SqlPhrase(phraseContext, sql);
    }

}