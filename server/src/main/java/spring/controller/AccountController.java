package spring.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import spring.form.ItemSubmissionForm;
import spring.form.LoginForm;
import spring.manager.AccountManager;
import spring.manager.OrderManager;
import spring.model.AccountInfo;
import spring.util.Authorities;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AccountController {
    private final Logger LOGGER = LogManager.getLogger(AccountController.class);

    private final AccountManager accountManager;
    private final OrderManager orderManager;
    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountController( AccountManager accountManager, OrderManager orderManager )
    {
        this.accountManager = accountManager;
        this.orderManager = orderManager;
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
            model.addAttribute("authorized",attachUserInfo(model, authenticationToken, orderManager));
        }
        return "login";
    }

    public static boolean attachUserInfo( Model model, UsernamePasswordAuthenticationToken authenticationToken, OrderManager orderManager)
    {
        if( authenticationToken == null) {
            model.addAttribute("viewType","customer");
            return false;
        }
        Account account = ((AccountDAO)authenticationToken.getPrincipal()).getAccount();
        AccountInfo accountInfo = new AccountInfo(account);
        model.addAttribute("accountInfo",accountInfo);
        model.addAttribute("viewType",account.getAccountType());
        if(account.getAccountType().equalsIgnoreCase(Authorities.EMPLOYEE.getAuthority())
                || account.getAccountType().equalsIgnoreCase(Authorities.MANAGER.getAuthority())
        ){
            model.addAttribute("itemSubmissionForm",new ItemSubmissionForm());
        }
        if(orderManager != null)
            model.addAttribute("cart", orderManager.loadCart(account));
        return true;
    }

    public static boolean attachUserInfo( Model model, UsernamePasswordAuthenticationToken authenticationToken )
    {
        return attachUserInfo(model,authenticationToken,null);
    }


    @GetMapping("/register")
    public String showRegisterForm( Model model,
                                  UsernamePasswordAuthenticationToken authenticationToken )
    {
        model.addAttribute("accountRegistrationForm", new AccountRegistrationForm());
        model.addAttribute("authorized",attachUserInfo(model, authenticationToken, orderManager));

        return "register";
    }

    @PostMapping( "/process_register")
    public String processRegister(
            @Validated @ModelAttribute("accountRegistrationForm") AccountRegistrationForm accountRegistrationForm )
    {

        String encodedPassword = passwordEncoder.encode(accountRegistrationForm.getPassword());

        LOGGER.info("Processing Register");
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
