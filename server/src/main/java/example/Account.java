package example;



import javax.persistence.*;

@Entity
public class Account {
    private @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    Long id;
    private final Username username;
    private final Password password;

    Account() {
        this.username = null;
        this.password = null;
    }

    Account(Username username, Password password)
    {
        this.username = username;
        this.password = password;
    }
    Account(Long id, Username username, Password password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId()
    {
        return id;
    }

    public Username getUsername()
    {
        return username;
    }

    public Password getPassword()
    {
        return password;
    }

    @PrePersist
    @PreUpdate
    void assertEncrypted() {
        if (!password.isEncrypted()) {
            throw new IllegalStateException("Tried to persist/load a user with a non-encrypted password!");
        }
    }
}
