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

/**
 * @name SecuritySettings
 *
 * Controls what pages are accessible and what is protected.
 * Also controls the authentication provider and password encoder.
 */
@Configuration
public class SecuritySettings extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final AccountRepository accountRepository;

    public SecuritySettings(
            DataSource dataSource,
            AccountRepository accountRepository
    ) {
        this.dataSource = dataSource;
        this.accountRepository = accountRepository;
    }

    /**
     * @name userDetailsService
     *
     * Creates a new AccountManager for the application.
     *
     * @return AccountManager The account manager.
     */
    @Bean
    public AccountManager userDetailsService() {
        return new AccountManager(accountRepository);
    }

    /**
     * @name passwordEncoder
     *
     * Creates a new BCryptPasswordEncoder for the application.
     *
     * @return BCryptPasswordEncoder The password encoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @name authenticationProvider
     *
     * Creates a new DaoAuthenticationProvider for the application.
     * Controls how the data is accessed and authenticated.
     *
     * @return DaoAuthenticationProvider The authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());

        return authenticationProvider;
    }

    /**
     * @name configure
     *
     * Controls the security configurations for the http requests.
     *
     * @param http HttpSecurity which is used to configure the security.
     * @throws Exception Unused, but required by the interface.
     */
    @Override
    protected void configure(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf().disable()// Disable Cross Site Request Forgery
                // Controls the access to certain webpages.
                .authorizeRequests()
                    // Allow all requests to these pages.
                    .antMatchers("/register*", // TODO Use SettingsUtil for endpoints.
                            "/process_register*",
                            "/login",
                            "/login?error=true",
                            "/test",
                            "/landing",
                            "/categories",
                            "/favicon.ico",
                            "/"
                    ).permitAll() // PermitAll = No login required.
                    // Allow all requests to these other pages if the user is logged in.
                    .anyRequest().authenticated()
                    // Add onto the above.
                    .and()
                // Create a login page.
                .formLogin(
                        // Use the following configuration for the login page.
                        httpSecurityFormLoginConfigurer -> {
                            // TODO Use SettingsUtil for endpoints.
                            httpSecurityFormLoginConfigurer.loginPage("/login"); // Set the endpoint for the login page.
                            httpSecurityFormLoginConfigurer.permitAll(); // Allow all users to access the login page.
                            httpSecurityFormLoginConfigurer.failureUrl("/login?error=true"); // Set the failure endpoint for the login page.
                            httpSecurityFormLoginConfigurer.defaultSuccessUrl("/"); // Set the default success endpoint for the login page.
                        }
                )
                // Create a logout page.
                .logout(
                        // Use the following configuration for the logout page.
                        logoutConfig ->{
                            // TODO Use SettingsUtil for endpoints.
                            logoutConfig.deleteCookies("JSESSIONID"); // Delete the session cookie.
                            logoutConfig.logoutUrl("/login?logout=true"); // Set the endpoint for the logout page.
                            logoutConfig.clearAuthentication(true); // Clear the authentication on the logout page.
                            logoutConfig.logoutSuccessUrl("/login"); // Set the success endpoint for the logout page.
                            logoutConfig.invalidateHttpSession(true); // Invalidate the session on the logout page.
                        }
                );
    }

    /**
     * @name configure
     *
     * When the application is started, the following configuration is used.
     *
     * @param builder AuthenticationManagerBuilder which is used to create the authentication manager.
     * @throws Exception Unused, but required by the interface.
     */
    @Override
    public void configure(
            AuthenticationManagerBuilder builder
    ) throws Exception {
        builder.authenticationProvider(authenticationProvider());
        //builder.jdbcAuthentication().dataSource(( DataSource ) this.getApplicationContext().getBean("dataSource"));
    }
}
