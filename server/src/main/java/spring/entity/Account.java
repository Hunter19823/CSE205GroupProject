package spring.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    private static final Logger LOGGER = LogManager.getLogger(Account.class);

    @Id
    @Column(nullable = false, unique = true, length = 45)
    private String username;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column()
    private String firstName;
    @Column()
    private String lastName;
    @Column()
    private String email;
    @Column()
    private String address;

    public Account()
    {
        this.username = "";
        this.password = "";
        this.enabled = false;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.address = "";
    }

    public Account( String username, String password, boolean enabled, String firstName, String lastName, String email, String address)
    {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
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
}
