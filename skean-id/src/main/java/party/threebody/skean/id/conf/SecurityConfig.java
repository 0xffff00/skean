/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.id.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	PermissionEvaluator permissionEvaluator;
	@Autowired
	DataSource dataSource0;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/about","/**").permitAll()
		.anyRequest().authenticated().and()
		.formLogin().and()
		.httpBasic().realmName("skean")
		.and()
		//.exceptionHandling().accessDeniedPage("/403")
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.jdbcAuthentication().dataSource(dataSource0).and()
		//.authenticationProvider(daoAuthenticationProvider())
		//.userDetailsService(userDetailsService);
		;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(){
		List<AuthenticationProvider> providers=Arrays.asList(daoAuthenticationProvider());
		ProviderManager manager=new ProviderManager(providers);
		return manager;
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider daoAuthProvider=new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService);
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean MethodSecurityExpressionHandler expressionHandler(){
		DefaultMethodSecurityExpressionHandler  handler=new DefaultMethodSecurityExpressionHandler();
		handler.setPermissionEvaluator(permissionEvaluator);
		return handler;
	}
}
