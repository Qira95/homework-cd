package home.work.homework.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class AppMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private final AuthoritySession authoritySession;

    public AppMethodSecurityExpressionRoot(Authentication authentication, AuthoritySession authoritySession) {
        super(authentication);
        this.authoritySession = authoritySession;
    }

    /**
     * Check if user performing operation has permission for it
     *
     * @param permission Permission to check
     * @return true if user has requested permission, false if not
     */
    public boolean hasPermission(String permission) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> permission.equalsIgnoreCase(a.getAuthority()));
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
