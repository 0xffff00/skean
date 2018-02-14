package party.threebody.skean.id.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("skean.id.oauth-auth-server")
public class AuthServerConfigProperties {
    private String clientId = "skean-id-authsrv-0";
    private String clientSecret;

    private String userPasswordStyle = "encrypt";
    private String inMemoryUserConfFilePath = "classpath:users.conf.json";

    private int refreshTokenValiditySeconds = 3600 * 24 * 60; // default 60 days.

    private int accessTokenValiditySeconds = 3600 * 24 * 7; // default 7 days.

    private String jwtSigningKey;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSercret) {
        this.clientSecret = clientSercret;
    }

    public String getUserPasswordStyle() {
        return userPasswordStyle;
    }

    public void setUserPasswordStyle(String userPasswordStyle) {
        this.userPasswordStyle = userPasswordStyle;
    }

    public String getInMemoryUserConfFilePath() {
        return inMemoryUserConfFilePath;
    }

    public void setInMemoryUserConfFilePath(String inMemoryUserConfFilePath) {
        this.inMemoryUserConfFilePath = inMemoryUserConfFilePath;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public int getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
