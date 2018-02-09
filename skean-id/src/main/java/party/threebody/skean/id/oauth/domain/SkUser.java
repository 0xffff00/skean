package party.threebody.skean.id.oauth.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import party.threebody.skean.collections.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public class SkUser {

    static final String DEFAULT_AUTHORITY = "ROLE_USER";
    private String username;
    private String password;
    private Set<String> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public User toSpringSecurityUser() {
        if (authorities == null || authorities.isEmpty()) {
            authorities = Sets.of(DEFAULT_AUTHORITY);
        } else {
            authorities.add(DEFAULT_AUTHORITY);
        }
        return new User(username, password,
                authorities.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList()));
    }
}
