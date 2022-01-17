package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.entity.Order;
import spring.form.CartModificationForm;
import spring.manager.AccountManager;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.OrderInfo;
import spring.model.ShoppingCartInfo;
import spring.util.Authorities;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * @name ShoppingCartController
 *
 * Controls the transactions and requests related to the shopping cart.
 */
@Controller
@Transactional
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingCartController.class);
    private final ItemManager itemManager;
    private final OrderManager orderManager;

    public ShoppingCartController(
            ItemManager itemManager,
            OrderManager orderManager
    ) {
        this.itemManager = itemManager;
        this.orderManager = orderManager;
    }

    /**
     * @name shoppingCartHandler
     *
     * Controls the get requests for a user's shopping cart.
     *
     * @param model The model to be used in the view.
     * @param authenticationToken The authentication token of the user.
     * @param pageable The pageable object.
     * @return A redirect or template name.
     */
    @GetMapping
    public String shoppingCartHandler(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        // Create and give a purchase form to the model.
        CartModificationForm cartModificationForm = new CartModificationForm();
        cartModificationForm.setOperation("purchase");
        model.addAttribute("orderSubmissionForm", cartModificationForm);
        // Attach the user's info to the model.
        model.addAttribute("authorized", AccountController.attachUserInfo(model, authenticationToken, orderManager));

        // Retrieve the user's account.
        Account account = ((AccountDAO) authenticationToken.getPrincipal()).getAccount();
        // If the user is not a customer, provide a list of pending orders.
        if (ShoppingCartController.isNotCustomer(account))
            model.addAttribute("pendingOrders", orderManager.findAllPendingOrders(pageable));
        else
            // Otherwise, provide a list of the user's saved orders.
            model.addAttribute("savedOrders", orderManager.findAllSavedOrdersByAccount(account));

        // Return the shopping cart template.
        return "cart";
    }

    /**
     * @name shoppingCartPostHandler
     *
     * Controls the post requests for a user's shopping cart.
     *
     * @param model The model to be used in the view.
     * @param request The request object.
     * @param authenticationToken The authentication token of the user.
     * @param cartModificationForm The form containing the modification request.
     * @param categoryid The (optional) category id of the item.
     * @param itemId The id of the item.
     * @return A redirect or template name.
     */
    @PostMapping
    public String shoppingCartPostHandler(
            Model model,
            HttpServletRequest request,
            UsernamePasswordAuthenticationToken authenticationToken,
            CartModificationForm cartModificationForm,
            @RequestParam(value = "categoryid", required = false) String categoryid,
            @RequestParam(value = "itemid", required = true) BigInteger itemId
    ) {
        // Retrieve the user's account.
        Account account = ((AccountDAO) authenticationToken.getPrincipal()).getAccount();
        // Create a new shopping cart model.
        ShoppingCartInfo cartInfo = new ShoppingCartInfo();

        try {
            // If the user is an employee or higher, perform the action.
            if (ShoppingCartController.isNotCustomer(account)) {
                itemManager.performAction(itemId, cartModificationForm);
            } else {
                // Null check the cart modification form.
                if (cartModificationForm == null)
                    throw new IllegalArgumentException("CartModification Form cannot be null!");

                // Load the current cart of the user.
                cartInfo = orderManager.loadCart(account);
                // Obtain the order info from the cart.
                OrderInfo order = cartInfo.getOrder(itemId);
                order.setItemID(itemId);
                order.setQuantity(cartModificationForm.getQuantity());

                // Handle the operation of the cart modification form.
                switch (cartModificationForm.getOperation()) {
                    // Handle the modification of the order quantity or removal.
                    case "modify":
                        // TODO move to it's own method in order Manager.
                        // If the item is being removed
                        if (cartModificationForm.getQuantity() <= 0) {
                            orderManager.deleteOrder(account, order);
                            cartInfo.deleteOrder(cartModificationForm.getItem_id());
                        } else {
                            // Otherwise, update the quantity and reattach the order.
                            OrderInfo orderInfo = orderManager.upsertOrder(account, order);
                            cartInfo.putOrder(orderInfo);
                        }
                        break;
                    // Handle the purchase of the order.
                    case "purchase":
                        // TODO move to it's own method in order Manager.
                        Optional<Order> orderOptional = orderManager.findSavedOrderByItemId(account, itemId);
                        if (orderOptional.isPresent())
                            orderManager.saveToPendingOrder(orderOptional.get());
                        break;
                    // Throw an error for invalid requests.
                    default:
                        throw new Exception("Action Not Supported.");
                }
            }
        } catch (Exception e) {
            // Log any errors for later debugging.
            e.printStackTrace();
            LOGGER.error(e);
            try {
                // Redirect them with an error message in the URI.
                return "redirect:" + request.getRequestURI() + "?error=true&message=" + URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Unknown Error", "UTF-8") + "&categoryid=" + categoryid;
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error(ex);
            }
        }
        // Attach cart information
        model.addAttribute("cart", cartInfo);
        return "redirect:" + request.getRequestURI();
    }

    /**
     * @name isNotCustomer
     *
     * Checks if the user has credentials higher than a customer.
     *
     * @param account The account to check.
     * @return True if the user is an employee or higher.
     */
    public static boolean isNotCustomer(
            Account account
    ) {
        return account.getAccountType().equalsIgnoreCase(Authorities.MANAGER.getAuthority()) || account.getAccountType().equalsIgnoreCase(Authorities.EMPLOYEE.getAuthority());
    }
}
