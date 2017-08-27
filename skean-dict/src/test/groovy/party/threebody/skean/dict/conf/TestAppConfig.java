package party.threebody.skean.dict.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.SqlPrinter;
import party.threebody.skean.jdbc.phrase.SqlBuilder;
import party.threebody.skean.jdbc.phrase.SqlBuilderConfig;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

import javax.sql.DataSource;

@Configuration
public class TestAppConfig {


    @Bean
    @Profile("memdb")
    public DataSource memdbDataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/test0.sql")
                .build();
        return db;
    }
    @Bean
    public JdbcTemplate jdbcTmpl(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean
    ChainedJdbcTemplate chainedJdbcTemplate(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        SqlBuilderConfig sqlBuilderConfig = new SqlBuilderConfig();
        sqlBuilderConfig.setEnableBackquote(true);
        SqlBuilder sqlBuilder = new SqlBuilderMysqlImpl(sqlBuilderConfig);

        ChainedJdbcTemplateContext pc = new ChainedJdbcTemplateContext();
        SqlPrinter sqlPrinter=new SqlPrinter(pc);
        pc.setDataSource(dataSource);
        pc.setJdbcTemplate(jdbcTemplate);
        pc.setSqlBuilder(sqlBuilder);
        pc.setSqlPrinter(sqlPrinter);
        pc.setPrintSqlAndResult(true);
        pc.setMaxCharsToPrintInOneLine(60);
        pc.setPrintSqlResultStrategy("REFLECTION");
        return new ChainedJdbcTemplate(pc);
    }

}