package home.work.homework.config;

import home.work.homework.security.LoggedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithTestSecurityContextHolder implements WithSecurityContextFactory<WithLoggedUser> {
    @Override
    public SecurityContext createSecurityContext(WithLoggedUser mockUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        LoggedUser loggedUser = new LoggedUser(
                mockUser.id(),
                mockUser.role()
        );

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loggedUser,
                "x-auth-token",
                loggedUser.getGrantedAuthorities()
        );

        context.setAuthentication(auth);
        return context;
    }

}