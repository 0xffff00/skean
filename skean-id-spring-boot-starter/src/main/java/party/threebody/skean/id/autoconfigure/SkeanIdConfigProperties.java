package party.threebody.skean.id.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("skean.id")
    public class SkeanIdConfigProperties {

    private String userConfFilePath = "classpath:users.conf.json";

    public String getUserConfFilePath() {
        return userConfFilePath;
    }

    public void setUserConfFilePath(String userConfFilePath) {
        this.userConfFilePath = userConfFilePath;
    }
}
