package example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserAccount {

    private static final Logger LOGGER = LogManager.getLogger(UserAccount.class);

    @Id
    @Column(nullable = false, unique = true, length = 45)
    private String username;
    @Column(nullable = false, length = 64)
    private String password;
    @Column(nullable = false)
    private boolean enabled = true;

    public UserAccount()
    {
        username = null;
        password = null;
        enabled = false;
    }

    public UserAccount( String username, String password, boolean enabled )
    {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
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
}
