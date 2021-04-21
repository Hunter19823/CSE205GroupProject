package spring.util;

import org.springframework.security.core.GrantedAuthority;

public enum Authorities implements GrantedAuthority {
    CUSTOMER,
    EMPLOYEE,
    MANAGER;

    @Override
    public String getAuthority()
    {
        return this.name().toLowerCase();
    }
}
