package example;



import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
public class Account {
    private @GeneratedValue(strategy = GenerationType.IDENTITY) @Id Long id;
    private final Username username;
    private final Password password;

    public Account() {
        this.id = 0L;
        this.username = null;
        this.password = null;
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
