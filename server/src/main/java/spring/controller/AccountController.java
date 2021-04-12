package spring.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.entity.Account;
import spring.manager.AccountManager;

@Controller
public class AccountController {
    private final AccountManager accountManager;

    public AccountController( AccountManager accountManager)
    {
        this.accountManager = accountManager;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object requestLogin( Model model)
    {
        System.out.println("Login post mapping");

        return "";
    }

    @GetMapping("/register")
    public String showRegisterForm( Model model )
    {
        model.addAttribute("userAccount",new Account());

        return "register";
    }

    @PostMapping( "/process_register")
    public String processRegister( Account userAccount )
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userAccount.getPassword());

        accountManager.registerAccount(
                userAccount.getUsername(),
                encodedPassword,
                userAccount.getFirstName(),
                userAccount.getLastName(),
                userAccount.getEmail(),
                userAccount.getAddress()
        );

        return "redirect:/items";
    }
}
