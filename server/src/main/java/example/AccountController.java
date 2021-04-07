package example;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AccountController {

    private final AuthorizationManagement authorizationManagement;

    public AccountController( AuthorizationManagement manager)
    {
        this.authorizationManagement = manager;
    }

    @GetMapping("/register")
    public String showRegisterForm( Model model )
    {
        model.addAttribute("userAccount",new UserAccount());

        return "register";
    }

    @PostMapping("/process_register")
    public Object processRegister( UserAccount userAccount )
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userAccount.getPassword());
        authorizationManagement.registerUser(userAccount.getUsername(),encodedPassword);

        RedirectView redirectView = new RedirectView("redirect:/login");
        redirectView.setPropagateQueryParams(true);

        return redirectView;
    }
}
