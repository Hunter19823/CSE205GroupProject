package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.manager.ItemManager;

@Controller
public class CategoryController {

    private final ItemManager itemManager;

    public CategoryController( ItemManager itemManager )
    {
        this.itemManager = itemManager;
    }


    @GetMapping("/landing")
    public String onIndexRequest( Model model )
    {
        return "landing";
    }
}
