package example.web;

import example.Account;
import example.AccountManagement;
import example.Password;
import example.Username;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class AccountController {
    private final Logger LOGGER = LogManager.getLogger(AccountController.class);
    private final AccountManagement accountManagement;

    @ModelAttribute("users")
    public Page<Account> users( @PageableDefault(size = 5) Pageable pageable )
    {
        return accountManagement.findAll(pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object register( UserForm userForm, BindingResult binding, Model model) {
        LOGGER.info("Register Request has been Received. "+userForm.toString());
        userForm.validate(binding, accountManagement);

        if (binding.hasErrors()) {
            return "";
        }

        accountManagement.register(new Username(userForm.getUsername()), Password.raw(userForm.getPassword()));

        RedirectView redirectView = new RedirectView("redirect:/users");
        redirectView.setPropagateQueryParams(true);

        return redirectView;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(Model model, UserForm userForm) {

        model.addAttribute("userForm", userForm);

        return "users";
    }


    interface UserForm {

        String getUsername();

        String getPassword();

        String getRepeatedPassword();

        /**
         * Validates the {@link UserForm}.
         *
         * @param errors
         * @param userManagement
         */
        default void validate(BindingResult errors, AccountManagement userManagement) {

            rejectIfEmptyOrWhitespace(errors, "username", "user.username.empty");
            rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty");
            rejectIfEmptyOrWhitespace(errors, "repeatedPassword", "user.repeatedPassword.empty");

            if (!getPassword().equals(getRepeatedPassword())) {
                errors.rejectValue("repeatedPassword", "user.password.no-match");
            }

            try {

                userManagement.findByUsername(new Username(getUsername())).ifPresent(
                        user -> errors.rejectValue("username", "user.username.exists"));

            } catch (IllegalArgumentException o_O) {
                errors.rejectValue("username", "user.username.invalidFormat");
            }
        }
    }
}
