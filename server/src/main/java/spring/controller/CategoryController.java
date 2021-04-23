package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.entity.Category;
import spring.entity.Item;
import spring.form.CartModificationForm;
import spring.form.ItemSubmissionForm;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.ItemModel;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class CategoryController {
    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private final ItemManager itemManager;
    private final OrderManager orderManager;

    public CategoryController( ItemManager itemManager, OrderManager orderManager)
    {
        this.itemManager = itemManager; this.orderManager = orderManager;
    }


    @GetMapping(value = {"/landing*","/","/categories",""})
    public String onIndexRequest( Model model,
                                  @PageableDefault(size = 5) Pageable pageable,
                                  UsernamePasswordAuthenticationToken authenticationToken
                              )
    {
        model.addAttribute("uri","/categories");
        model.addAttribute("categories", itemManager.findAllCategories(pageable));
        model.addAttribute("authorized",AccountController.attachUserInfo(model, authenticationToken, orderManager));

        return "landing";
    }

    @PostMapping(value = "/item")
    public String onItemSubmissionRequest(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            @Validated ItemSubmissionForm itemSubmissionForm,
            @RequestParam(value = "categoryid", required = false) String category,
            @PageableDefault(size = 5) Pageable pageable
    )
    {
        model.addAttribute("authorized",AccountController.attachUserInfo(model, authenticationToken, orderManager));
        if( authenticationToken == null)
            return onCategoryRequest(model,Pageable.unpaged(), null,category);
        Account account = ((AccountDAO)authenticationToken.getPrincipal()).getAccount();

        Item item = itemManager.registerItem(itemSubmissionForm.getName(),itemSubmissionForm.getDescription(), itemSubmissionForm.getStock(), itemSubmissionForm.getPrice(), category);

        if(category == null || category == "")
            return "redirect:/landing";
        return onCategoryRequest(model,pageable, null,category);
    }

    @GetMapping(value = "/category")
    public String onCategoryRequest( Model model,
                                     @PageableDefault(size = 5) Pageable pageable,
                                     UsernamePasswordAuthenticationToken authenticationToken,
                                     @RequestParam(value = "categoryid",required = false) String categoryName
    )
    {
        Optional<Category> categoryOptional = itemManager.findCategoryById(categoryName);
        if(!categoryOptional.isPresent()) {
            try {
                return "redirect:/landing?error=true&message="+ URLEncoder.encode("Category Not Found", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e);
            }
        }
        model.addAttribute("uri","/category");
        model.addAttribute("authorized",AccountController.attachUserInfo(model, authenticationToken,orderManager));

        Page<BigInteger> itemPage = itemManager.findItemIdsInCategory(categoryName, pageable);
        model.addAttribute("itemPage",itemPage);

        Map<BigInteger, ItemModel> itemModelMap = new LinkedHashMap<>();
        itemManager.findAllItemsInList(itemPage).forEach( item -> {
            itemModelMap.put(item.getUuid(), new ItemModel(item));
        });
        model.addAttribute("itemMap",itemModelMap);
        model.addAttribute("cartModificationForm", new CartModificationForm());

        return "category";
    }
}
