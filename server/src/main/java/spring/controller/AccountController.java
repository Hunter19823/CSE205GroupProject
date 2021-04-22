package spring.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.form.AccountRegistrationForm;
import spring.form.LoginForm;
import spring.manager.AccountManager;
import spring.model.AccountInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AccountController {
    private final Logger LOGGER = LogManager.getLogger(AccountController.class);
    private final AccountManager accountManager;

    public AccountController( AccountManager accountManager )
    {
        this.accountManager = accountManager;
    }

    @GetMapping( "/login" )
    public String onLoginRequest( Model model,
                                  @RequestParam(name = "logout",required = false) Boolean logout,
                                  HttpServletRequest request,
                                  UsernamePasswordAuthenticationToken authenticationToken
    )
    {
        model.addAttribute("login", new LoginForm());

        if(logout != null && logout){
            HttpSession session = request.getSession(false);
            if(session != null)
                session.invalidate();
            for(Cookie cookie : request.getCookies())
                cookie.setMaxAge(0);
            model.addAttribute("authorized",false);
        }else{
            model.addAttribute("authorized",attachUserInfo(model, authenticationToken));
        }
        return "login";
    }

    public static boolean attachUserInfo( Model model, UsernamePasswordAuthenticationToken authenticationToken )
    {
        if( authenticationToken == null)
            return false;
        Account account = ((AccountDAO)authenticationToken.getPrincipal()).getAccount();
        AccountInfo accountInfo = new AccountInfo(account);
        model.addAttribute("accountInfo",accountInfo);
        return true;
    }


    @GetMapping("/register")
    public String showRegisterForm( Model model )
    {
        model.addAttribute("accountRegistrationForm", new AccountRegistrationForm());

        return "register";
    }

    @PostMapping( "/process_register")
    public String processRegister(
            @Validated @ModelAttribute("accountRegistrationForm") AccountRegistrationForm accountRegistrationForm )
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(accountRegistrationForm.getPassword());

        System.out.println("Processing Register");
        accountManager.registerAccount(
                accountRegistrationForm.getUsername(),
                encodedPassword,
                accountRegistrationForm.getFirstName(),
                accountRegistrationForm.getLastName(),
                accountRegistrationForm.getEmail(),
                accountRegistrationForm.getAddress()
        );

        return "redirect:/login";
    }
}
