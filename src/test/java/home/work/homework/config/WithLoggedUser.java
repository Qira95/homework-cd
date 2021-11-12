package home.work.homework.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithTestSecurityContextHolder.class)
public @interface WithLoggedUser {

    /**
     * User's id
     *
     * @return ID of the LoggedUser
     */
    String id() default "42";

    /**
     * Specifies user role
     *
     * eg. CLIENT, BACKOFFICE
     *
     * @return String role of the user
     */
    String role() default "CLIENT";
}
