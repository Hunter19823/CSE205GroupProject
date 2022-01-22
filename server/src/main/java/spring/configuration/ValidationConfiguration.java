package spring.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import spring.form.AccountRegistrationForm;
import spring.form.LoginForm;
import spring.util.AccountRegistrationValidator;
import spring.util.ItemSubmissionValidator;
import spring.util.LoginFormValidator;

/**
 * @name ValidationConfiguration
 *
 * Controls the validation and data binding of the forms.
 */
@Controller
public class ValidationConfiguration {
    private final AccountRegistrationValidator accountRegistrationValidator;
    private final ItemSubmissionValidator itemSubmissionValidator;
    private final LoginFormValidator loginFormValidator;

    public ValidationConfiguration(
            AccountRegistrationValidator accountRegistrationValidator,
            ItemSubmissionValidator itemSubmissionValidator,
            LoginFormValidator loginFormValidator
    ) {
        this.accountRegistrationValidator = accountRegistrationValidator;
        this.itemSubmissionValidator = itemSubmissionValidator;
        this.loginFormValidator = loginFormValidator;
    }

    /**
     * @name dataBinder
     *
     * Attaches a validator to each form.
     *
     * @param dataBinder The form being validated.
     */
    @InitBinder
    public void dataBinder(
            WebDataBinder dataBinder
    ) {
        Object data = dataBinder.getTarget();
        if(data != null){
            if(data.getClass() == AccountRegistrationForm.class){
                dataBinder.setValidator(accountRegistrationValidator);
            }
            if(data.getClass() == ItemSubmissionValidator.class) {
                dataBinder.setValidator(itemSubmissionValidator);
            }
            if(data.getClass() == LoginForm.class){
                dataBinder.setValidator(loginFormValidator);
            }
        }
    }
}
