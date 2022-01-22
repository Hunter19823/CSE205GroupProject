package spring.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.entity.Account;
import spring.util.Authorities;

import java.util.Collection;
import java.util.Collections;

/**
 * @name AccountDAO
 *
 * The AccountDAO class is the data access object for the Account entity.
 * This DAO is also used in the Spring Security configuration for describing UserDetails.
 */
public class AccountDAO implements UserDetails {
    private final Account account;

    public AccountDAO( Account account ) {
        this.account = account;
    }

    /**
     * @name getAccount
     *
     * Returns the account entity.
     *
     * @return the account entity.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @name getFirstName
     *
     * Returns the first name of the account.
     *
     * @return the first name of the account.
     */
    public String getFirstName() {
        return account.getFirstName();
    }

    /**
     * @name getLastName
     *
     * Returns the last name of the account.
     *
     * @return the last name of the account.
     */
    public String getLastName() {
        return account.getLastName();
    }

    /**
     * @name getEmail
     *
     * Returns the email of the account.
     *
     * @return the email of the account.
     */
    public String getEmail() {
        return account.getEmail();
    }

    /**
     * @name getAddress
     *
     * Returns the address of the account.
     *
     * @return the address of the account.
     */
    public String getAddress() {
        return account.getAddress();
    }

    /**
     * @name getAuthorities
     *
     * Get all the authorities for the account.
     *
     * @return a collection of GrantedAuthority objects the user has.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(Authorities.valueOf(account.getAccountType().toUpperCase()));
    }

    /**
     * @name getPassword
     *
     * This method is required by the UserDetails interface.
     * This method will return an encrypted password once a user has registered.
     *
     * @return the encrypted password.
     */
    @Override
    public String getPassword() {
        return account.getPassword();
    }

    /**
     * @name getUsername
     *
     * This method is required by the UserDetails interface.
     * This method is used to get the username of the user.
     *
     * @return the username of the user.
     */
    @Override
    public String getUsername() {
        return account.getUsername();
    }

    /**
     * @name isAccountNonExpired
     *
     * This method is required by the UserDetails interface.
     * This will always return true as long as the account is enabled.
     *
     * @return true if the account is enabled.
     */
    @Override
    public boolean isAccountNonExpired() {
        return account.isEnabled();
    }

    /**
     * @name isAccountNonLocked
     *
     * This method is required by the UserDetails interface.
     * This will always return true as long as the account is enabled.
     *
     * @return true if the account is enabled.
     */
    @Override
    public boolean isAccountNonLocked() {
        return account.isEnabled();
    }

    /**
     * @name isCredentialsNonExpired
     *
     * This method is required by the UserDetails interface.
     * This will always return true as long as the account is enabled.
     *
     * @return true if the account is enabled.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return account.isEnabled();
    }

    /**
     * @name isEnabled
     *
     * This method is required by the UserDetails interface.
     * This method determines if an account is enabled or not.
     *
     * @return true if the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }


}
