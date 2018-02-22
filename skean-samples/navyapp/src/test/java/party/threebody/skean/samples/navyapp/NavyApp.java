package party.threebody.skean.samples.navyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * mainly test for skean-web
 */
@SpringBootApplication
public class NavyApp {
    public static void main(String[] args) {
        SpringApplication.run(NavyApp.class, args);
    }

}


