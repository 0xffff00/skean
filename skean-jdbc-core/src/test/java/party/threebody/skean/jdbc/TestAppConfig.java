package party.threebody.skean.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
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
                .addScript("db/h2-init.sql")
                .addScript("db/test0.sql")
                .addScript("db/test1.sql")
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
        SqlBuilder sqlBuilder = new SqlBuilderMysqlImpl(sqlBuilderConfig);
        SqlPrinterConfig sqlPrinterConfig = new SqlPrinterConfig();
        SqlPrinter sqlPrinter = new SqlPrinter(sqlPrinterConfig);

        return new ChainedJdbcTemplate(dataSource, jdbcTemplate, sqlBuilder, sqlPrinter);
    }

}
