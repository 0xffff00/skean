package party.threebody.s4g.conf.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DataConfig.class, MvcConfig.class, SecurityConfig.class })
@ComponentScan(basePackages = { "party.threebody" })
public class RootConfig {

}