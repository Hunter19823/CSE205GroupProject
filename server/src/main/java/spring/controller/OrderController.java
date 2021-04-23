package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.manager.AccountManager;
import spring.manager.ItemManager;
import spring.manager.OrderManager;

import java.math.BigInteger;

@Controller
public class OrderController {
    private final ItemManager itemManager;
    private final OrderManager orderManager;
    private final AccountManager accountManager;

    public OrderController( ItemManager itemManager, OrderManager orderManager, AccountManager accountManager )
    {
        this.itemManager = itemManager;
        this.orderManager = orderManager;
        this.accountManager = accountManager;
    }


    @PostMapping("/updateorder")
    public String updateOrder(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            @RequestParam("itemid")BigInteger itemId
    )
    {

        return "cart";
    }

}
