package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.entity.Order;
import spring.entity.PendingOrder;
import spring.manager.AccountManager;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

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

    @GetMapping("/purchase")
    public String onPurchase(
            Model model,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken
    )
    {
        return "redirect:/shoppingCart";
    }

    @GetMapping("/processOrder")
    public String onFinishedOrder(
            Model model,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken
    )
    {
        return "redirect:/shoppingCart";
    }

    @PostMapping("/processOrder")
    public String processOrder(
            Model model,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken,
            @RequestParam(value = "pendingid", required = true) BigInteger pendingid
    ) throws AccessDeniedException
    {

        Optional<PendingOrder> orderOptional = orderManager.findPendingOrderById(pendingid);
        if(orderOptional.isPresent()) {
            PendingOrder pendingOrder = orderOptional.get();
            orderManager.deleteOrder(pendingOrder.getAccount(),new OrderInfo(pendingOrder.getOrder()));
        }
        return "redirect:"+request.getRequestURI();
    }


    @PostMapping("/purchase")
    public String updateOrder(
            Model model,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken,
            @RequestParam(value = "orderid", required = true) BigInteger orderid
    )
    {

        Optional<Order> orderOptional = orderManager.findSavedOrderById(orderid);
        if(orderOptional.isPresent())
            orderManager.saveToPendingOrder(orderOptional.get());

        return "redirect:"+request.getRequestURI();
    }

}
