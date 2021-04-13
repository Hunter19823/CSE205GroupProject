package spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import spring.form.AccountForm;
import spring.util.AccountFormValidator;
import spring.util.ItemFormValidator;

@Controller
public class ValidationConfiguration {
    @Autowired
    private AccountFormValidator accountFormValidator;
    @Autowired
    private ItemFormValidator itemFormValidator;

    @InitBinder
    public void dataBinder( WebDataBinder dataBinder )
    {
        Object data = dataBinder.getTarget();
        if(data != null){
            if(data.getClass() == AccountForm.class){
                dataBinder.setValidator(accountFormValidator);
            }
            if(data.getClass() == ItemFormValidator.class) {
                dataBinder.setValidator(itemFormValidator);
            }
        }
    }
}
