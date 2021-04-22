package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.manager.ItemManager;

@Controller
public class CategoryController {
    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private final ItemManager itemManager;

    public CategoryController( ItemManager itemManager )
    {
        this.itemManager = itemManager;
    }


    @GetMapping(value = {"/landing","/","/categories",""})
    public String onIndexRequest( Model model,
                                  @PageableDefault(size = 5) Pageable pageable,
                                  UsernamePasswordAuthenticationToken authenticationToken)
    {
        model.addAttribute("uri","/categories");
        model.addAttribute("categories", itemManager.findAllCategories(pageable));
        model.addAttribute("authorized",AccountController.attachUserInfo(model, authenticationToken));

        return "landing";
    }
}
