package spring.manager;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.repositories.AccountRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountManager implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AccountManager(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account registerAccount(String username, String password, String firstName, String lastName,
                                   String email, String address, String accountType) {
        // TODO validate input
        String[] invalidCharacters = {"=", "'", "-", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "["
                , "]", ":", ";", "<", ">", "?", "/", "|"};
        if (username == null)
            throw new IllegalArgumentException("Don't leave your username empty");
        if (password == null)
            throw new IllegalArgumentException("Don't leave your password empty");
        if (firstName == null)
            throw new IllegalArgumentException("Don't leave your first name empty");
        if (lastName == null)
            throw new IllegalArgumentException("Don't leave your last name empty");
        if (email == null)
            throw new IllegalArgumentException("Don't leave your email empty");
        if (address == null)
            throw new IllegalArgumentException("Don't leave your address empty");
        if (username.length() > 20)
            throw new IllegalArgumentException("Sorry, your username has too many characters, please shorten it");
        if (accountRepository.existsById(username))
            throw new IllegalArgumentException("Username has been taken. Please enter a new name");
        for (int i = 0; i < invalidCharacters.length; i++) {
            if (username.contains(invalidCharacters[i]))
                throw new IllegalArgumentException("Sorry, but we do not support special characters for usernames, please" +
                        " enter a valid username");
        }
        if (!email.contains("@"))
            throw new IllegalArgumentException("Please enter a valid email address");
        for (int i = 0; i < invalidCharacters.length; i++) {
            if (address.contains(invalidCharacters[i]))
                throw new IllegalArgumentException("Please enter a valid address");
        }
        if (accountType != null)
            return accountRepository.save(new Account(username, password, true, firstName, lastName, email, address, accountType));

        return accountRepository.save(new Account(username, password, true, firstName, lastName, email, address));
    }

    @Override
    public AccountDAO loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findById(username);
        if (!account.isPresent())
            throw new UsernameNotFoundException("Account not found");
        return new AccountDAO(account.get());
    }
}
