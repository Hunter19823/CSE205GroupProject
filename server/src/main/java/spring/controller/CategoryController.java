package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.entity.Category;
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

    public CategoryController(ItemManager itemManager, OrderManager orderManager) {
        this.itemManager = itemManager;
        this.orderManager = orderManager;
    }

    @GetMapping(value = {"/landing*", "/", "/categories", ""})
    public String onIndexRequest(Model model,
                                 @PageableDefault(size = 5) Pageable pageable,
                                 UsernamePasswordAuthenticationToken authenticationToken
    ) {
        Page<Category> categoryPage = itemManager.findAllCategories(pageable);

        model.addAttribute("url", "/categories");
        model.addAttribute("categories", categoryPage);
        model.addAttribute("paginationStart", Math.max(Math.min(pageable.getPageNumber() - 4, categoryPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageable.getPageNumber() + 5, 10), categoryPage.getTotalPages()));

        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

        return "landing";
    }

    @PostMapping(value = "/item")
    public String onItemSubmissionRequest(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            @Validated ItemSubmissionForm itemSubmissionForm,
            @RequestParam(value = "categoryid", required = false) String category,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        if (authenticationToken == null)
            return onCategoryRequest(model, Pageable.unpaged(), null, category);

        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

//        Account account = ((AccountDAO) authenticationToken.getPrincipal()).getAccount();
        itemManager.registerItem(itemSubmissionForm.getName(), itemSubmissionForm.getDescription(), itemSubmissionForm.getStock(), itemSubmissionForm.getPrice(), category);

        if (category == null || category.equals(""))
            return "redirect:/landing";
        return onCategoryRequest(model, pageable, authenticationToken, category);
    }

    @GetMapping(value = "/category")
    public String onCategoryRequest(Model model,
                                    @PageableDefault(size = 5) Pageable pageable,
                                    UsernamePasswordAuthenticationToken authenticationToken,
                                    @RequestParam(value = "categoryid", required = false) String categoryName
    ) {
        Optional<Category> categoryOptional = itemManager.findCategoryById(categoryName);
        if (!categoryOptional.isPresent()) {
            try {
                return "redirect:/landing?error=true&message=" + URLEncoder.encode("Category Not Found", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e);
            }
        }

        Page<BigInteger> itemPage = itemManager.findItemIdsInCategory(categoryName, pageable);
        Map<BigInteger, ItemModel> itemModelMap = new LinkedHashMap<>();

        itemManager.findAllItemsInList(itemPage).forEach(item -> itemModelMap.put(item.getUuid(), new ItemModel(item)));

        model.addAttribute("itemPage", itemPage);
        model.addAttribute("url", "/category");
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

        model.addAttribute("paginationStart", Math.max(Math.min(pageable.getPageNumber() - 5, itemPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageable.getPageNumber() + 4, 10), itemPage.getTotalPages()));

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("itemMap", itemModelMap);
        model.addAttribute("cartModificationForm", new CartModificationForm());

        return "category";
    }
}
