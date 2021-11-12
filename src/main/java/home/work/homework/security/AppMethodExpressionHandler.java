package home.work.homework.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AppMethodExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final AuthoritySession authoritySession;

    public AppMethodExpressionHandler(AuthoritySession authoritySession) {
        this.authoritySession = authoritySession;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
                                                                              MethodInvocation invocation) {
        final AppMethodSecurityExpressionRoot root = new AppMethodSecurityExpressionRoot(authentication,
                authoritySession);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());

        return root;
    }
}