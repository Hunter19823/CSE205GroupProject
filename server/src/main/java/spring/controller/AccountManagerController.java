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

@Controller
public class AccountManagerController {
    private final AccountRepository accountRepository;
    static final String MANAGE_ENDPOINT = "manage";

    public AccountManagerController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping(AccountManagerController.MANAGE_ENDPOINT)
    public String handleManageRequest(
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize, @RequestParam Optional<String> sortBy,
            UsernamePasswordAuthenticationToken authenticationToken, Model model
    ) {

        if (AccountManagerController.isNotManager(authenticationToken)) return "redirect:/landing";

        attachPageAttributes(model, page, pageSize, sortBy);
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, null));

        return AccountManagerController.MANAGE_ENDPOINT;
    }

    @PostMapping(value = AccountManagerController.MANAGE_ENDPOINT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String handleManageChanges(
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> sortBy,
            UsernamePasswordAuthenticationToken authenticationToken, AccountManagerForm accountManagerForm, Model model
    ) {
        if (AccountManagerController.isNotManager(authenticationToken)) return "redirect:/landing";

        for (AccountManagerForm.User user : accountManagerForm.users)
            this.accountRepository.updateAuthorityById(user.getUsername(), user.getAuthority());

        attachPageAttributes(model, page, pageSize, sortBy);
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, null));

        return "redirect:/" + AccountManagerController.MANAGE_ENDPOINT;
    }

    public void attachPageAttributes(Model model, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> sortBy) {
        int pageNumber = page.orElse(0), pageSizeNumber = pageSize.orElse(5);
        String selectedSortBy = sortBy.orElse(AccountManagerForm.SORTABLE_FIELDS[0]);

        Pageable currentPage = PageRequest.of(pageNumber, pageSizeNumber, Sort.by(selectedSortBy));
        Page<Account> accountPage = this.accountRepository.findAll(currentPage);

        model.addAttribute("url", AccountManagerController.MANAGE_ENDPOINT);

        model.addAttribute("pageSizeOptions", AccountManagerForm.PAGE_SIZE_OPTIONS);
        model.addAttribute("pageSortingOptions", AccountManagerForm.SORTABLE_FIELDS);
        model.addAttribute("selectedSortingOption", selectedSortBy);

        //mathmatic
        model.addAttribute("paginationStart", Math.max(Math.min(pageNumber - 5, accountPage.getTotalPages() - 9), 1));
        model.addAttribute("paginationEnd", Math.min(Math.max(pageNumber + 4, 10), accountPage.getTotalPages()));

        model.addAttribute("accountPage", accountPage);
        model.addAttribute("accountTypes", Authorities.values());

    }

    public void attachChanges(Model model, int changes) {
        model.addAttribute("changes", changes);
    }

    public static boolean isNotManager(UsernamePasswordAuthenticationToken token) {
        Account account = ((AccountDAO) token.getPrincipal()).getAccount();
        return !account.getAccountType().equalsIgnoreCase(Authorities.MANAGER.getAuthority());
    }
}