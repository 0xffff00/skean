package party.threebody.herd.conf;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

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
@PropertySource("classpath:jdbc.properties")
class DataSourceConfig {

	@Autowired
	Environment env;
	@Bean
	@Profile("!memdb")
	public DataSource defaultDataSource() {
		final HikariDataSource ds = new HikariDataSource();
		ds.setMaximumPoolSize(20);
		ds.setDriverClassName("org.mariadb.jdbc.Driver");
		ds.setJdbcUrl(env.getProperty("jdbc.url"));
		ds.setUsername(env.getProperty("jdbc.username"));
		ds.setPassword(env.getProperty("jdbc.password"));
		ds.setMaximumPoolSize(Integer.valueOf(env.getProperty("jdbc.maximumPoolSize")));
		ds.setAutoCommit(false);
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
