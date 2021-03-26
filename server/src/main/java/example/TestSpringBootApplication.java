package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;


@SpringBootApplication
public class TestSpringBootApplication {

    public TestSpringBootApplication()
    {}

    @Autowired
    AccountManagement accountManagement;

    @PostConstruct
    public void init(){
        IntStream.range(0, 100).forEach(index -> {
            try {
                accountManagement.register(new Username("user" + index), Password.raw("foobar"));
            }catch (IllegalArgumentException e){}
        });
    }

    public @Bean PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
