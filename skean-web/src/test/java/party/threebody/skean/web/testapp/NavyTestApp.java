package party.threebody.skean.web.testapp;

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
public class NavyTestApp {

    public static void main(String[] args) {
        SpringApplication.run(NavyTestApp.class, args);
    }

}



