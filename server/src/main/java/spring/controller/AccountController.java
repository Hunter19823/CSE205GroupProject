package spring.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.form.AccountForm;
import spring.manager.AccountManager;

@Controller
public class AccountController {
    private final AccountManager accountManager;

    public AccountController( AccountManager accountManager)
    {
        this.accountManager = accountManager;
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
