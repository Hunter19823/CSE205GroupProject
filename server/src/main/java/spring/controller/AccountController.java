package spring.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spring.form.AccountForm;
import spring.form.LoginForm;
import spring.manager.AccountManager;

@Controller
public class AccountController {
    private final AccountManager accountManager;

    public AccountController( AccountManager accountManager )
    {
        this.accountManager = accountManager;
    }

    @GetMapping( "/login" )
    public String onLoginRequest( Model model )
    {
        model.addAttribute("login", new LoginForm());

        return "login";
    }


    @GetMapping("/register")
    public String showRegisterForm( Model model )
    {
        model.addAttribute("accountForm", new AccountForm());

        return "register";
    }

    @PostMapping( "/process_register")
    public String processRegister( @Validated AccountForm accountForm )
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(accountForm.getPassword());

        System.out.println("Processing Register");
        accountManager.registerAccount(
                accountForm.getUsername(),
                encodedPassword,
                accountForm.getFirstName(),
                accountForm.getLastName(),
                accountForm.getEmail(),
                accountForm.getAddress()
        );

        return "redirect:/login";
    }
}
