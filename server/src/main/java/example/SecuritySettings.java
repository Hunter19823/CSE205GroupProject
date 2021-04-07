package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;

import javax.sql.DataSource;
import java.util.Collection;

@Configuration
public class SecuritySettings extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http
                .authorizeRequests()
                    .antMatchers("/register","/login", "/home", "/").permitAll()
                    .antMatchers("/items").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login");
    }

    @Override
    public void configure( AuthenticationManagerBuilder builder) throws Exception
    {
        builder.jdbcAuthentication().dataSource(( DataSource ) this.getApplicationContext().getBean("dataSource"));
    }
}
