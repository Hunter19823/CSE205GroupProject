package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
import java.util.Optional;

/**
 * @name AccountController
 *
 * This class is the controller for the account page.
 * Anything regarding a user's account and the webpages associated with it are handled here.
 * This class controls the login, logout, and registration pages.
 */
@Controller
public class AccountController {
    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Logger LOGGER = LogManager.getLogger(AccountController.class);
    private final AccountManager accountManager;
    private final OrderManager orderManager;
    // TODO add static variables for login endpoints. Then Move to SettingsUtil

    public AccountController(
            AccountManager accountManager,
            OrderManager orderManager
    ) {
        this.accountManager = accountManager;
        this.orderManager = orderManager;
    }


    /**
     * @name onLoginRequest
     *
     * Creates and controls the login page.
     * Controls any request made to the login page.
     * Attaches the AccountInfo object to the model.
     *
     * @param model The model being passed to the view.
     * @param logout Whether the user is trying to log out or not.
     * @param request The request object. Contains the session token.
     * @param authenticationToken The authentication token.
     * @return The template or redirect page to render.
     */
    @GetMapping("/login")
    public String onLoginRequest(
            Model model,
            @RequestParam(name = "logout", required = false) Boolean logout,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken
    ) {
        // Attach the login form to the model.
        model.addAttribute("login", new LoginForm());

        // If the user is trying to log out, remove the session token, and de-authorize the user.
        if (logout != null && logout) {
            HttpSession session = request.getSession(false);
            if (session != null)
                session.invalidate();
            for (Cookie cookie : request.getCookies())
                cookie.setMaxAge(0);
            model.addAttribute("authorized", false);
        } else {
            // Otherwise, attach the user's info to the model.
            model.addAttribute("authorized", attachUserInfo(model, authenticationToken, orderManager));
        }
        // Return the user to the login page.
        return "login";
    }

    /**
     * @name attachUserInfo
     *
     * Attaches the user's info to the model.
     *
     * @param model The model being passed to the view.
     * @param authenticationToken The authentication token.
     * @param orderManager The order manager.
     * @return Whether the user is authorized or not.
     */
    public static boolean attachUserInfo(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            OrderManager orderManager
    ) {
        // Determine if the user's view is authorized or if it is an anonymous customer. (Not logged in)
        if (authenticationToken == null) {
            model.addAttribute("viewType", "customer");
            return false;
        }
        // Get the user's info from the database.
        Account account = ((AccountDAO) authenticationToken.getPrincipal()).getAccount();
        AccountInfo accountInfo = new AccountInfo(account);

        // Attach the user's info to the model.
        model.addAttribute("accountInfo", accountInfo);
        // Attach the user's access level to the model.
        model.addAttribute("viewType", account.getAccountType());

        // Attach an employee's order submission form to the model.
        if (account.getAccountType().equalsIgnoreCase(Authorities.EMPLOYEE.getAuthority())
                || account.getAccountType().equalsIgnoreCase(Authorities.MANAGER.getAuthority())
        ) {
            model.addAttribute("itemSubmissionForm", new ItemSubmissionForm());
        }
        // Attach the user's shopping cart to the model.
        if (orderManager != null)
            model.addAttribute("cart", orderManager.loadCart(account));
        return true;
    }

    /**
     * @name showRegisterForm
     *
     * Creates and controls the register page.
     * Controls any request made to the register page.
     * Attaches the account register form to the model.
     * Attaches the account info to the model.
     *
     * @param model The model being passed to the view.
     * @param authenticationToken The authentication token.
     * @return The template or redirect page to render.
     */
    @GetMapping("/register")
    public String showRegisterForm(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken
    ) {

        model.addAttribute("accountRegistrationForm", new AccountRegistrationForm());
        model.addAttribute("authorized", attachUserInfo(model, authenticationToken, orderManager));

        return "register";
    }

    /**
     * @name processRegisterForm
     *
     * Controls the Post request made to the process_register page.
     *
     * @param accountRegistrationForm The validated account registration form.
     * @param managerInvoked Whether the manager was invoked.
     * @param employeeInvoked Whether the employee was invoked.
     * @return The template or redirect page to render.
     */
    @PostMapping("/process_register")
    public String processRegister(
            @Validated @ModelAttribute("accountRegistrationForm") AccountRegistrationForm accountRegistrationForm,
            @RequestParam Optional<Boolean> managerInvoked,
            @RequestParam Optional<Boolean> employeeInvoked
    ) {
        // Encrypt the password.
        // Yes we recognize how insecure it is to use
        // HTTP POST requests to send sensitive data.
        // This is nothing more than a demo project.
        // At least it's encrypted in the database.
        String encodedPassword = passwordEncoder.encode(accountRegistrationForm.getPassword());

        LOGGER.info("Processing Register");
        // Send the account information to the account manager to request a new account.
        accountManager.registerAccount(
                accountRegistrationForm.getUsername(),
                encodedPassword,
                accountRegistrationForm.getFirstName(),
                accountRegistrationForm.getLastName(),
                accountRegistrationForm.getEmail(),
                accountRegistrationForm.getAddress(),
                accountRegistrationForm.getAccountType()
        );

        // If the manager was invoked, redirect to the manager page.
        if(managerInvoked.orElse(false))
            return "redirect:/manage";
        // If the employee was invoked, redirect to the employee page.
        if(employeeInvoked.orElse(false))
            return "redirect:/landing";
        // Otherwise, redirect to the login page.
        return "redirect:/login";
    }
}
