package party.threebody.skean.id.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
public class SkeanIdApp {
    public static void main(String[] args) {
        SpringApplication.run(SkeanIdApp.class, args);
    }
}
