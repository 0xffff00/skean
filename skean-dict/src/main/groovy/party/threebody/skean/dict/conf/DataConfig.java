package party.threebody.skean.dict.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ImportResource({ "classpath:spring-tx.xml" })
public class DataConfig {

	@Autowired
	Environment env;


	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTmpl(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate npJdbcTmpl(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}



}
@Configuration
class DataSourceConfig {


	@Bean
	@Profile("!memdb")
	public DataSource defaultDataSource() {
		HikariConfig config = new HikariConfig("/jdbc.properties");
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	
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
}
