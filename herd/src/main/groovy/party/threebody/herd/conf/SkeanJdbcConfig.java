package party.threebody.herd.conf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.ChainedJdbcTemplateContext;
import party.threebody.skean.jdbc.SqlPrinter;
import party.threebody.skean.jdbc.phrase.SqlBuilder;
import party.threebody.skean.jdbc.phrase.SqlBuilderConfig;
import party.threebody.skean.jdbc.phrase.SqlBuilderMysqlImpl;

import javax.sql.DataSource;

@Configuration
public class SkeanJdbcConfig {

	@Autowired
	Environment env;

	@Bean
	ChainedJdbcTemplate chainedJdbcTemplate(DataSource dataSource, JdbcTemplate jdbcTemplate) {
		SqlBuilderConfig sqlBuilderConfig = new SqlBuilderConfig();
		sqlBuilderConfig.setEnableBackquote(true);
		sqlBuilderConfig.setEnableModifyAllRows(true);
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

	@Bean
	ObjectMapper jacksonObjectMapper() {
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objMapper;
	}

}
