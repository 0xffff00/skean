package party.threebody.skean.samples.navyapp;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import party.threebody.skean.misc.SkeanInvalidArgumentException;
import party.threebody.skean.web.util.SkeanResources;

import java.util.List;
@Deprecated
//@Configuration
//@EnableWebSecurity
public class SecConfiguration extends WebSecurityConfigurerAdapter {

//    static final Logger logger = LoggerFactory.getLogger(SecConfiguration.class);
//
//    @Autowired ApplicationContext applicationContext;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService())
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .exceptionHandling()
////.authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").authenticated()
//                .antMatchers("/ships/**").permitAll()
//                .and()
//                .formLogin()
//                .successHandler(new MySavedRequestAwareAuthenticationSuccessHandler())
//                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout();
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
//    }

    // only support load from file now
//    @Bean
//    public UserDetailsService userDetailsService() {
//        List<SkUser> skUsers = SkeanResources.readValueFromLocalJsonFile(
//                "classpath:users.conf.json",
//                new TypeReference<List<SkUser>>() {
//                });
//        if (skUsers == null) {
//            throw new SkeanInvalidArgumentException("No any available users!");
//        }
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        for (SkUser u : skUsers) {
//            manager.createUser(u.toSpringSecurityUser());
//        }
//        return manager;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        AuthenticationManager manager = super.authenticationManagerBean();
//        return manager;
//    }

//    @Bean
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true);
//        firewall.setAllowSemicolon(true);
//        return firewall;
//    }
}