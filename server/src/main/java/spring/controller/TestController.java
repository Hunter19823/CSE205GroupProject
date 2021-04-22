package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.ShoppingCartInfo;
import spring.util.Authorities;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class TestController {
    private static final Logger LOGGER = LogManager.getLogger(TestController.class);
    private final ItemManager itemManager;

    private final OrderManager orderManager;

    public TestController( ItemManager itemManager, OrderManager orderManager ){
        this.itemManager = itemManager;
        this.orderManager = orderManager;
    }

    @RequestMapping("/test")
    public String getTestPage( HttpServletRequest request, Model model, @PageableDefault(size = 5) Pageable pageable, UsernamePasswordAuthenticationToken authenticationToken)
    {
        model.addAttribute("items", itemManager.findAllItemsByPageable(pageable));
        model.addAttribute("shoppingCart", new ShoppingCartInfo());
        LOGGER.info("Authorization Token: {}",authenticationToken);
        if(authenticationToken!= null) {
            LOGGER.info("Granted Authorities: {}",authenticationToken.getAuthorities().size());
            authenticationToken.getAuthorities().forEach(
                    token -> LOGGER.info("Authority: {}",token)
            );
            LOGGER.info("Credentials: {}",authenticationToken.getCredentials());
            Account account = ((AccountDAO)authenticationToken.getPrincipal()).getAccount();
            LOGGER.info("Account Username: {}",account.getUsername());
            LOGGER.info("Account Type: {}",account.getAccountType());
            LOGGER.info("Account Email: {}",account.getEmail());
        }
        LOGGER.info(request.getRemoteUser());
        LOGGER.info(request.isRequestedSessionIdValid());
        LOGGER.info(request.getAuthType());
        LOGGER.info("Is Customer? {}",request.isUserInRole(Authorities.CUSTOMER.getAuthority()));
        LOGGER.info("Is Employee? {}",request.isUserInRole(Authorities.EMPLOYEE.getAuthority()));
        LOGGER.info("Is Manager? {}",request.isUserInRole(Authorities.MANAGER.getAuthority()));

        return "test";
    }

}
