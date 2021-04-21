package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.ShoppingCartInfo;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    private final ItemManager itemManager;

    private final OrderManager orderManager;

    public TestController( ItemManager itemManager, OrderManager orderManager ){
        this.itemManager = itemManager;
        this.orderManager = orderManager;
    }

    @RequestMapping("/test")
    public String getTestPage( HttpServletRequest request, Model model, @PageableDefault(size = 5) Pageable pageable)
    {
        model.addAttribute("items", itemManager.findAllItems(pageable));
        model.addAttribute("shoppingCart", new ShoppingCartInfo());

        return "test";
    }

}
