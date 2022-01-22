package spring.entity;

import spring.util.Authorities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Set;

import static spring.util.SettingUtil.ACCOUNT_TYPE_LENGTH;
import static spring.util.SettingUtil.ADDRESS_LENGTH;
import static spring.util.SettingUtil.EMAIL_LENGTH;
import static spring.util.SettingUtil.FIRST_NAME_LENGTH;
import static spring.util.SettingUtil.LAST_NAME_LENGTH;
import static spring.util.SettingUtil.PASSWORD_LENGTH;
import static spring.util.SettingUtil.USERNAME_LENGTH;

/**
 * @name Account
 *
 * This class represents the database entity for the users table.
 */
@Entity
@Table(name = "users")
public class Account {
    /**
     * The unique username of the user.
     * This is also used as the primary key.
     */
    @Id
    @Column(name = "username", length = USERNAME_LENGTH, nullable = false, unique = true)
    private String username;

    /**
     * The encrypted password of the user.
     */
    @Column(name = "password", length = PASSWORD_LENGTH, nullable = false)
    private String password;

    /**
     * Whether the user's account is enabled.
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    /**
     * First name of the user.
     */
    @Column(name = "firstname", length = FIRST_NAME_LENGTH, nullable = false)
    private String firstName;

    /**
     * Last name of the user
     */
    @Column(name = "lastname", length = LAST_NAME_LENGTH, nullable = false)
    private String lastName;


    /**
     * Email of the user
     */
    @Column(name = "email", length = EMAIL_LENGTH, nullable = false)
    private String email;

    /**
     * Address of the user
     */
    @Column(name = "address", length = ADDRESS_LENGTH, nullable = false)
    private String address;

    /**
     * Authority level of the user
     */
    @Column(name = "accounttype", length = ACCOUNT_TYPE_LENGTH, nullable = false)
    private String accountType;

    /**
     * Set of saved orders associated with the user
      */
    @OneToMany(mappedBy = "account")
    private Set<Order> saved_orders;

    /**
     * Set of pending orders associated with the user
     */
    @OneToMany(mappedBy = "account")
    private Set<PendingOrder> pending_orders;



    public Account()
    {
        this.username = "";
        this.password = "";
        this.enabled = true;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.address = "";
        this.accountType = Authorities.CUSTOMER.getAuthority();
    }

    public Account(
            String username,
            String password,
            boolean enabled,
            String firstName,
            String lastName,
            String email,
            String address
    ) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.accountType = Authorities.CUSTOMER.getAuthority();
    }

    public Account(
            String username,
            String password,
            boolean enabled,
            String firstName,
            String lastName,
            String email,
            String address,
            String authority
    ) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.accountType = authority;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType( String accountType )
    {
        this.accountType = accountType;
    }

    public Set<Order> getSaved_orders()
    {
        return saved_orders;
    }

    public void setSaved_orders( Set<Order> saved_orders )
    {
        this.saved_orders = saved_orders;
    }

    public Set<PendingOrder> getPending_orders()
    {
        return pending_orders;
    }

    public void setPending_orders( Set<PendingOrder> pending_orders )
    {
        this.pending_orders = pending_orders;
    }
}
