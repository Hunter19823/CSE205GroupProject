package spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spring.manager.AccountManager;
import spring.repositories.AccountRepository;

import javax.sql.DataSource;

@Configuration
public class SecuritySettings extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final AccountRepository accountRepository;


    public SecuritySettings( DataSource dataSource, AccountRepository accountRepository )
    {
        this.dataSource = dataSource;
        this.accountRepository = accountRepository;
    }

    @Bean
    public AccountManager userDetailsService() {
        return new AccountManager(accountRepository);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());

        return authenticationProvider;
    }


    @Override
    protected void configure( HttpSecurity http ) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/register*",
                            "/process_register*",
                            "/login",
                            "/login?error=true",
                            "/test",
                            "/landing",
                            "/categories",
                            "/"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin(
                        httpSecurityFormLoginConfigurer -> {
                            httpSecurityFormLoginConfigurer.loginPage("/login");
                            httpSecurityFormLoginConfigurer.permitAll();
                            httpSecurityFormLoginConfigurer.failureUrl("/login?error=true");
                            httpSecurityFormLoginConfigurer.defaultSuccessUrl("/");
                        }
                )
                .logout(
                        logoutConfig ->{
                            logoutConfig.deleteCookies("JSESSIONID");
                            logoutConfig.logoutUrl("/login?logout=true");
                            logoutConfig.clearAuthentication(true);
                            logoutConfig.logoutSuccessUrl("/login");
                            logoutConfig.invalidateHttpSession(true);
                        }
                );
    }

    @Override
    public void configure( AuthenticationManagerBuilder builder) throws Exception
    {
        builder.authenticationProvider(authenticationProvider());
        //builder.jdbcAuthentication().dataSource(( DataSource ) this.getApplicationContext().getBean("dataSource"));
    }
}
