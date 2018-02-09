package party.threebody.skean.id.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ResourceUtils;
import party.threebody.skean.id.oauth.domain.SkUser;
import party.threebody.skean.lang.ObjectMappers;
import party.threebody.skean.misc.SkeanNotImplementedException;

import java.io.IOException;
import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired ApplicationContext applicationContext;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<SkUser> skUsers=SkeanResources.readValueFromLocalJsonFile(
                "classpath:users.conf.json",
                new TypeReference<List<SkUser>>() {
        });

        if (skUsers!=null){ // load to memory
            InMemoryUserDetailsManager m = new InMemoryUserDetailsManager();
            skUsers.forEach(u->{
                m.createUser(u.toSpringSecurityUser());
            });
            return m;
        }
        throw new SkeanNotImplementedException();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/*").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}
