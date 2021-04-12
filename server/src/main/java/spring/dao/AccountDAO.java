package spring.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.entity.Account;

import java.util.Collection;

public class AccountDAO implements UserDetails {
    private Account account;


    public AccountDAO( Account account)
    {
        this.account = account;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount( Account account )
    {
        this.account = account;
    }

    public String getFirstName()
    {
        return account.getFirstName();
    }

    public String getLastName()
    {
        return account.getLastName();
    }

    public String getEmail()
    {
        return account.getEmail();
    }

    public String getAddress()
    {
        return account.getAddress();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return account.getPassword();
    }

    @Override
    public String getUsername()
    {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return account.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return account.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return account.isEnabled();
    }

    @Override
    public boolean isEnabled()
    {
        return account.isEnabled();
    }


}
