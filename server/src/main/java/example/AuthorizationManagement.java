package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthorizationManagement {

    @Autowired
    UserRepository repository;

    public AuthorizationManagement()
    {

    }

    public UserAccount registerUser( String username, String password )
    {
        Assert.notNull(username, "Username must not be null!");
        Assert.notNull(password, "Password must not be null!");
        return repository.save(new UserAccount(username,password,true));
    }
}
