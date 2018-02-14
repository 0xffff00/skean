package party.threebody.skean.id.autoconfigure;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import party.threebody.skean.id.autoconfigure.domain.SkUser;
import party.threebody.skean.misc.SkeanInvalidArgumentException;
import party.threebody.skean.web.util.SkeanResources;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SkeanIdConfigProperties.class)
public class SkeanWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    static final Logger logger = LoggerFactory.getLogger(SkeanWebSecurityConfiguration.class);

    private final SkeanIdConfigProperties skeanIdConf;
    @Autowired ApplicationContext applicationContext;


    public SkeanWebSecurityConfiguration(SkeanIdConfigProperties skeanIdConf) {
        this.skeanIdConf = skeanIdConf;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    // only support load from file now
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        List<SkUser> skUsers = SkeanResources.readValueFromLocalJsonFile(
                skeanIdConf.getUserConfFilePath(),
                new TypeReference<List<SkUser>>() {
                });
        if (skUsers == null) {
            throw new SkeanInvalidArgumentException("No any available users!");
        }

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        for (SkUser u : skUsers) {
            manager.createUser(u.toSpringSecurityUser());
        }
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/*").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}
