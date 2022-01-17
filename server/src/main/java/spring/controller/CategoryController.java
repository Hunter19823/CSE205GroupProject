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

/**
 * @name CategoryController
 *
 * Controls the default landing page, aka the item categories page.
 * Also controls submissions of new items.
 */
@Controller
public class CategoryController {
    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    private final ItemManager itemManager;
    private final OrderManager orderManager;

    public CategoryController(
            ItemManager itemManager,
            OrderManager orderManager
    ) {
        this.itemManager = itemManager;
        this.orderManager = orderManager;
    }

    /**
     * @name onIndexRequest
     *
     * Creates and returns the landing page template.
     *
     * @param model The model to be used in the view.
     * @param pageable The pageable object to be used in the view.
     * @param authenticationToken The authentication token of the viewer.
     * @return The template or redirect to render.
     */
    @GetMapping(value = {"/landing*", "/", "/categories", ""})
    public String onIndexRequest(
            Model model,
            @PageableDefault(size = 8) Pageable pageable,
            UsernamePasswordAuthenticationToken authenticationToken
    ) {
        // Find all the categories.
        Page<Category> categoryPage = itemManager.findAllCategories(pageable);

        // Add the url to the page for use in the template.
        model.addAttribute("url", "/categories"); // TODO Replace with SettingsUtil constant
        // Add the categories and page details to the model.
        model.addAttribute("categories", categoryPage);
        model.addAttribute("paginationStart", Math.max(Math.min(pageable.getPageNumber() - 4, categoryPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageable.getPageNumber() + 5, 10), categoryPage.getTotalPages()));
        // Add authorization level to the model.
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

        // Return the landing page template.
        return "landing";
    }

    /**
     * @name onItemSubmissionRequest
     *
     * Manages the httppost requests to the item submission page.
     *
     * @param model The model to be used in the view.
     * @param authenticationToken The authentication token of the viewer.
     * @param itemSubmissionForm The item submission form being received.
     * @param category The category of the item being submitted.
     * @param pageable The pageable object to be used in the view.
     * @return The template or redirect to render.
     */
    @PostMapping(value = "/item")
    public String onItemSubmissionRequest(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            @Validated ItemSubmissionForm itemSubmissionForm,
            @RequestParam(value = "categoryid", required = false) String category,
            @PageableDefault(size = 4) Pageable pageable
    ) {
        // Basic authentication check.
        if (authenticationToken == null)
            return onCategoryRequest(model, Pageable.unpaged(), null, category);

        // Attach authorization level to the model.
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

        // TODO verify the account's authorization level.
        //Account account = ((AccountDAO) authenticationToken.getPrincipal()).getAccount();
        itemManager.registerItem(itemSubmissionForm.getName(), itemSubmissionForm.getDescription(), itemSubmissionForm.getStock(), itemSubmissionForm.getPrice(), category);

        // Redirect the user to the landing page if the category request is invalid.
        if (category == null || category.equals(""))
            return "redirect:/landing";

        // Redirect the user back to the category the item was added from.
        return onCategoryRequest(model, pageable, authenticationToken, category);
    }

    /**
     * @name onCategoryRequest
     *
     * Manages the httpget requests to the category page.
     *
     * @param model The model to be used in the view.
     * @param pageable The pageable object to be used in the view.
     * @param authenticationToken The authentication token of the viewer.
     * @param categoryName The (optional) name of the category being requested.
     * @return The template or redirect to render.
     */
    @GetMapping(value = "/category")
    public String onCategoryRequest(
            Model model,
            @PageableDefault(size = 4) Pageable pageable,
            UsernamePasswordAuthenticationToken authenticationToken,
            @RequestParam(value = "categoryid", required = false) String categoryName
    ) {
        // Check to see if the category name is valid and the category exists.
        Optional<Category> categoryOptional = itemManager.findCategoryById(categoryName);
        if (!categoryOptional.isPresent()) {
            try {
                return "redirect:/landing?error=true&message=" + URLEncoder.encode("Category Not Found", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e);
            }
        }

        // Collect a page of ItemIDs in the category.
        Page<BigInteger> itemPage = itemManager.findItemIdsInCategory(categoryName, pageable);
        // Create a map of item models for the view to display.
        Map<BigInteger, ItemModel> itemModelMap = new LinkedHashMap<>();
        itemManager.findAllItemsInList(itemPage).forEach(item -> itemModelMap.put(item.getUuid(), new ItemModel(item)));

        // Attach redirect information to the model.
        model.addAttribute("url", "/category");
        // Attach authorization level to the model.
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));
        // Attach paging information to the model.
        model.addAttribute("itemPage", itemPage);
        model.addAttribute("paginationStart", Math.max(Math.min(pageable.getPageNumber() - 5, itemPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageable.getPageNumber() + 4, 10), itemPage.getTotalPages()));

        // Attach category information to the model including the items in the category.
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("itemMap", itemModelMap);
        // Attach the add to cart form to the model.
        model.addAttribute("cartModificationForm", new CartModificationForm());

        return "category";
    }
}
