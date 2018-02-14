package party.threebody.skean.id.oauth;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import party.threebody.skean.id.oauth.domain.SkUser;
import party.threebody.skean.misc.SkeanInvalidArgumentException;
import party.threebody.skean.misc.SkeanNotImplementedException;
import party.threebody.skean.web.util.SkeanResources;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired AuthServerConfigProperties authServerConfigProperties;

    static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired ApplicationContext applicationContext;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    // only support load from file now
    @Bean
    public UserDetailsService userDetailsService() {
        List<SkUser> skUsers = SkeanResources.readValueFromLocalJsonFile(
                authServerConfigProperties.getInMemoryUserConfFilePath(),
                new TypeReference<List<SkUser>>() {
                });
        if (skUsers == null) {
            throw new SkeanInvalidArgumentException("No any available users!");
        }
        if (authServerConfigProperties.getUserPasswordStyle().equals("encrypted")) {
            // no op
        } else if (authServerConfigProperties.getUserPasswordStyle().equals("plain")) {
            skUsers = skUsers.stream()
                    .map(u -> u.toEncyptedInstance(passwordEncoder())).collect(Collectors.toList());
        } else {
            throw new SkeanNotImplementedException("wrong style: " + authServerConfigProperties.getUserPasswordStyle());
        }

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        for (SkUser u : skUsers) {
            manager.createUser(u.toSpringSecurityUser());
        }
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
