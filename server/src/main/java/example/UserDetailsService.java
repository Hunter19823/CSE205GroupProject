package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;


public class UserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException
    {
        Optional<UserAccount> user = userRepository.findById(username);
        if(!user.isPresent())
            throw new UsernameNotFoundException("User not found");
        return new UserDetails(user.get());
    }
}
