package example;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthorizationManagement authorizationManagement;


    public LoginController( AuthorizationManagement manager)
    {
        this.authorizationManagement = manager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        System.out.println("Login get mapping");
        return "login.html";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object requestLogin( Model model)
    {
        System.out.println("Login post mapping");

        return "";
    }
}
