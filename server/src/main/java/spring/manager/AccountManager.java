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

    public AccountManager( AccountRepository accountRepository )
    {
        this.accountRepository = accountRepository;
    }


    public Account registerAccount( String username, String password, String firstName, String lastName, String email, String address){
        // TODO validate input

        return accountRepository.save(new Account(username,password,true, firstName, lastName, email, address));
    }

    @Override
    public AccountDAO loadUserByUsername( String username ) throws UsernameNotFoundException
    {
        Optional<Account> account = accountRepository.findById(username);
        if(!account.isPresent())
            throw new UsernameNotFoundException("Account not found");
        return new AccountDAO(account.get());
    }


}
