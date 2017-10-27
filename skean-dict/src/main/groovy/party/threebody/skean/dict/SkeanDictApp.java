package party.threebody.skean.dict;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.SqlPrinter;
import party.threebody.skean.jdbc.phrase.SqlBuilder;
import party.threebody.skean.jdbc.phrase.SqlBuilderConfig;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

import javax.sql.DataSource;

@SpringBootApplication
public class SkeanDictApp {

    public static void main(String[] args) {
        SpringApplication.run(SkeanDictApp.class, args);
    }

}

@Configuration
class JdbcConfig {

    @Autowired
    Environment env;

    @Bean
    ChainedJdbcTemplate chainedJdbcTemplate(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        SqlBuilderConfig sqlBuilderConfig = new SqlBuilderConfig();
        sqlBuilderConfig.setEnableBackquote(true);
        SqlBuilder sqlBuilder = new SqlBuilderMysqlImpl(sqlBuilderConfig);

        ChainedJdbcTemplateContext pc = new ChainedJdbcTemplateContext();
        SqlPrinter sqlPrinter = new SqlPrinter(pc);
        pc.setDataSource(dataSource);
        pc.setJdbcTemplate(jdbcTemplate);
        pc.setSqlBuilder(sqlBuilder);
        pc.setSqlPrinter(sqlPrinter);
        pc.setPrintSqlAndResult(true);
        pc.setMaxCharsToPrintInOneLine(60);
        pc.setPrintSqlResultStrategy("REFLECTION");
        return new ChainedJdbcTemplate(pc);
    }

    @Bean
    ObjectMapper jacksonObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objMapper;
    }

}

@Configuration
class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("X-Total-Count", "X-Total-Affected");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // enable matrix variables support
        final UrlPathHelper urlPathHelper = new UrlPathHelper();
        configurer.setUrlPathHelper(urlPathHelper);
        configurer.getUrlPathHelper().setRemoveSemicolonContent(false);
    }


}
