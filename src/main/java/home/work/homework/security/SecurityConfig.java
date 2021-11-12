package home.work.homework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public FilterChainProxy springSecurityFilterChain() {
        List<SecurityFilterChain> securityFilterChains = new LinkedList<>();
        securityFilterChains.add(new DefaultSecurityFilterChain
                (new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())));

        securityFilterChains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/**"), Arrays.asList(
                new SecurityContextPersistenceFilter(new HttpSessionSecurityContextRepository()),
                new SecurityFilter()
        )));

        return new FilterChainProxy(securityFilterChains);
    }
}
