package example;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class AccountManagement {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    public Account register(Username username, Password password)
    {
        Assert.notNull(username,"Username must not be null!");
        Assert.notNull(username,"Password must not be null!");

        repository.findByUsername(username).ifPresent(user ->{
            throw new IllegalArgumentException("User with that name already exists!");
        });

        Password encryptedPassword = Password.encrypted(encoder.encode(password));

        return repository.save(new Account(username,encryptedPassword));
    }

    public Page<Account> findAll( Pageable pageable) {

        Assert.notNull(pageable, "Pageable must not be null!");

        return repository.findAll(pageable);
    }
    public Optional<Account> findByUsername( Username username) {

        Assert.notNull(username, "Username must not be null!");

        return repository.findByUsername(username);
    }
}
