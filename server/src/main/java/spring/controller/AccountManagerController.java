package spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.form.AccountManagerForm;
import spring.repositories.AccountRepository;
import spring.util.Authorities;
import java.util.Optional;

/**
 * @name AccountManagerController
 *
 * This class is the controller for the manager page.
 */
@Controller
public class AccountManagerController {
    private final AccountRepository accountRepository;
    public static final String MANAGE_ENDPOINT = "manage"; // TODO move to SettingsUtil

    public AccountManagerController(
            AccountRepository accountRepository
    ) {
        this.accountRepository = accountRepository;
    }

    /**
     * @name handleManageRequest
     *
     * Creates and controls the manager page.
     * This method is used to handle the get requests to the manager account page.
     *
     * @param page The page number to be displayed.
     * @param pageSize The number of items per page.
     * @param sortBy The field to sort by.
     * @param authenticationToken The authentication token.
     * @param model The model to attach the page attributes to.
     * @return The template or redirect page to render.
     */
    @GetMapping(AccountManagerController.MANAGE_ENDPOINT)
    public String handleManageRequest(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy,
            UsernamePasswordAuthenticationToken authenticationToken,
            Model model
    ) {
        // Do a quick check to see if the user is a manager.
        if (AccountManagerController.isNotManager(authenticationToken))
            return "redirect:/landing"; // TODO move to SettingsUtil

        // Attach the page attributes to the model.
        attachPageAttributes(model, page, pageSize, sortBy);
        // Attach the account info to the model.
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, null));

        // Return to the manager account page.
        return AccountManagerController.MANAGE_ENDPOINT;
    }

    /**
     * @name handleManageChanges
     *
     * Controls the httppost requests to the manager page.
     *
     * @param page The page number to be displayed.
     * @param pageSize The number of items per page.
     * @param sortBy The field to sort by.
     * @param authenticationToken The authentication token.
     * @param accountManagerForm The form containing the changes to be made.
     * @param model The model to attach the page attributes to.
     * @return The template or redirect page to render.
     */
    @PostMapping(value = AccountManagerController.MANAGE_ENDPOINT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleManageChanges(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy,
            UsernamePasswordAuthenticationToken authenticationToken,
            AccountManagerForm accountManagerForm, Model model
    ) {
        // Do a quick check to see if the user is a manager.
        if (AccountManagerController.isNotManager(authenticationToken)) return "redirect:/landing";

        // Get and modify all the accounts that were modified by the manager.
        for (AccountManagerForm.User user : accountManagerForm.users)
            this.accountRepository.updateAuthorityById(user.getUsername(), user.getAuthority());

        // Attach the page attributes to the model.
        attachPageAttributes(model, page, pageSize, sortBy);
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, null));

        // Redirect to the manager account page.
        return "redirect:/" + AccountManagerController.MANAGE_ENDPOINT;
    }

    /**
     * @name attachPageAttributes
     *
     * A helper method for attaching the page attributes to the model.
     * Handles the pagination and sorting of the accounts.
     *
     * @param model The model to attach the page attributes to.
     * @param page The page number to be displayed.
     * @param pageSize The number of items per page.
     * @param sortBy The field to sort by.
     */
    public void attachPageAttributes(
            Model model,
            Optional<Integer> page,
            Optional<Integer> pageSize,
            Optional<String> sortBy
    ) {
        // Get the requested page number, if none is provided, default to the first page (page zero).
        // Get the requested page size, if none is provided, default to the default page size.
        int pageNumber = page.orElse(0), pageSizeNumber = pageSize.orElse(5);
        // Get the requested sort field, if none is provided, default to the default sort field. (username)
        String selectedSortBy = sortBy.orElse(AccountManagerForm.SORTABLE_FIELDS[0]);

        // Get the actual Pageable object and use that to query the repository.
        Pageable currentPage = PageRequest.of(pageNumber, pageSizeNumber, Sort.by(selectedSortBy));
        Page<Account> accountPage = this.accountRepository.findAll(currentPage);

        // Attach the url for the pagination to the model.
        model.addAttribute("url", AccountManagerController.MANAGE_ENDPOINT);
        // Attach the page attributes to the model.
        model.addAttribute("pageSizeOptions", AccountManagerForm.PAGE_SIZE_OPTIONS);
        model.addAttribute("pageSortingOptions", AccountManagerForm.SORTABLE_FIELDS);
        model.addAttribute("selectedSortingOption", selectedSortBy);
        model.addAttribute("paginationStart", Math.max(Math.min(pageNumber - 5, accountPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageNumber + 4, 10), accountPage.getTotalPages()));
        model.addAttribute("accountPage", accountPage);
        // Attach all the possible authority levels.
        model.addAttribute("accountTypes", Authorities.values());
    }

    /**
     * @name isNotManager
     *
     * A helper method for checking if the user is a manager.
     * This will query the database do ensure that the user is a manager.
     *
     * @param token The authentication token to check.
     * @return true if the user is a manager, false otherwise.
     */
    public static boolean isNotManager(
            UsernamePasswordAuthenticationToken token
    ) {
        // Retrieve the user from the token.
        Account account = ((AccountDAO) token.getPrincipal()).getAccount();
        // Check if the user is a manager.
        return !account.getAccountType().equalsIgnoreCase(Authorities.MANAGER.getAuthority());
    }
}