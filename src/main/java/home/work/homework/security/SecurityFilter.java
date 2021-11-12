package home.work.homework.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class SecurityFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String jwt = httpRequest.getHeader("Authorization");

        if (StringUtils.isBlank(jwt)) {
            LOG.error("Unable to find user JWT token");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Base64.Decoder decoder = Base64.getDecoder();
        String userData = new String(decoder.decode(jwt.split("\\.")[1]));

        LoggedUser loggedUser;
        try {
            loggedUser = LoggedUser.fromJson(userData);
        } catch (Exception e) {
            LOG.error("Unable to load logged user");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        authenticateSpringContext(loggedUser);
        filterChain.doFilter(request, response);
    }

    private void authenticateSpringContext(LoggedUser loggedUser) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loggedUser, null, loggedUser.getGrantedAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
