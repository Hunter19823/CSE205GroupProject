package example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemManagement itemController;

    public ItemController(ItemManagement controller)
    {
        this.itemController = controller;
    }

    @ModelAttribute("items")
    public Page<Item> items( @PageableDefault(size = 5) Pageable pageable )
    {
        return itemController.findAll(pageable);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers( Model model) {

        return "items";
    }
}
