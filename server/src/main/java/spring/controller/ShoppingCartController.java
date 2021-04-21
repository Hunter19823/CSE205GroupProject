package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.manager.AccountManager;
import spring.manager.ItemManager;
import spring.manager.OrderManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Controller
@Transactional
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private final ItemManager itemManager;
    private final AccountManager accountManager;
    private final OrderManager orderManager;

    public ShoppingCartController( ItemManager itemManager, AccountManager accountManager, OrderManager orderManager )
    {
        this.itemManager = itemManager;
        this.accountManager = accountManager;
        this.orderManager = orderManager;
    }

    @RequestMapping
    public String shoppingCartHandler( HttpServletRequest request, Model model ){
        // TODO
        System.out.println(request.getCookies().length);
        for(Cookie cookie : request.getCookies()){
            System.out.println(cookie.getName());
        }
        System.out.println(request.getRemoteUser());

        return "shoppingCart";
    }

    @RequestMapping("/shoppingCartRemoveItem")
    public String removeFromShoppingCartHandler( HttpServletRequest request, Model model ){
        // TODO
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/shoppingCartAddItem")
    public String addToShoppingCartHandler( HttpServletRequest request, Model model ){
        // TODO
        return "redirect:/shoppingCart";
    }
}
