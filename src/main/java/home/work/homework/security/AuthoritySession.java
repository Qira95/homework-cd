package home.work.homework.security;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthoritySession {

    private static final Logger logger = getLogger(AuthoritySession.class);

    private LoggedUser user;

    public LoggedUser getLoggedUser() {
        if (user == null) {
            loadUser();
        }

        return user;
    }

    private void loadUser() {
        this.user = (LoggedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (this.user == null) {
            logger.error("Can not find logged user.");
            throw new AuthenticationCredentialsNotFoundException("No credentials were found");
        }
    }
}
