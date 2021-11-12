package home.work.homework.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class LoggedUser {

    private static final Logger logger = getLogger(LoggedUser.class);

    private final String id;
    private final UserRole role;

    @JsonCreator
    public LoggedUser(@JsonProperty("id") String id,
                      @JsonProperty("role") String role) {
        this.id = id;
        this.role = UserRole.valueOf(role);
    }

    public String getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    @JsonIgnore
    public Set<GrantedAuthority> getGrantedAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.toString()));
    }

    public static LoggedUser fromJson(String jsonRepresentation) {
        try {
            return new ObjectMapper()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(jsonRepresentation, LoggedUser.class);
        } catch (Exception e) {
            logger.error("Unable to convert JSON={} to LoggedUser", jsonRepresentation, e);
            throw new AuthenticationCredentialsNotFoundException("No valid credentials found");
        }
    }

}
