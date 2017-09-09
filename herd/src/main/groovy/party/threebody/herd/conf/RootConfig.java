package party.threebody.herd.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:herd.properties"})
@Import({ DataConfig.class, MvcConfig.class })
@ComponentScan(basePackages = { "party.threebody" })
public class RootConfig {

}