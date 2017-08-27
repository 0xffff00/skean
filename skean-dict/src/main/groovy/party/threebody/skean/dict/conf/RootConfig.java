package party.threebody.skean.dict.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DataConfig.class, MvcConfig.class })
@ComponentScan(basePackages = { "party.threebody" })
public class RootConfig {

}