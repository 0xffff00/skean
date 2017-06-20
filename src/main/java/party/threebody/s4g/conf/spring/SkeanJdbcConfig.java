package party.threebody.s4g.conf.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.phrase.SqlBuilder;
import party.threebody.skean.jdbc.phrase.SqlBuilderConfig;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

@Configuration
public class SkeanJdbcConfig {

	@Autowired
	Environment env;

	@Bean
	ChainedJdbcTemplate chainedJdbcTemplate(DataSource dataSource, JdbcTemplate jdbcTmpl) {
		SqlBuilderConfig sqlBuilderConfig = new SqlBuilderConfig();
		SqlBuilder sqlBuilder = new SqlBuilderMysqlImpl(sqlBuilderConfig);
		
		ChainedJdbcTemplateContext pc = new ChainedJdbcTemplateContext(dataSource, jdbcTmpl, sqlBuilder);
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
