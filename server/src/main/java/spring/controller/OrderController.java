package spring.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.entity.Order;
import spring.entity.PendingOrder;
import spring.manager.OrderManager;
import spring.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

/**
 * @name OrderController
 *
 * The class that handles the order page and the order creation.
 */
@Controller
public class OrderController {
    private final OrderManager orderManager;

    public OrderController(
            OrderManager orderManager
    ) {
        this.orderManager = orderManager;
    }

    /**
     * @name onPurchase
     *
     * This method is to prevent invalid page access.
     * Redirects to the shopping cart page.
     *
     * @return The redirect URL.
     */
    @GetMapping("/purchase")
    public String onPurchase() {
        return "redirect:/shoppingCart";
    }

    /**
     * @name onFinishedOrder
     *
     * This method is to prevent invalid page access.
     * Redirects to the shopping cart page.
     *
     * @return The redirect URL.
     */
    @GetMapping("/processOrder")
    public String onFinishedOrder() {
        return "redirect:/shoppingCart";
    }

    /**
     * @name processOrder
     *
     * Handles the httppost request to delete an existing order.
     *
     * @param request The request object.
     * @param pendingid The pending order id.
     * @return The redirect URL.
     * @throws AccessDeniedException If the user lacks the permission to delete the order.
     */
    @PostMapping("/processOrder")
    public String processOrder(
            HttpServletRequest request,
            @RequestParam(value = "pendingid", required = true) BigInteger pendingid
    ) throws AccessDeniedException {
        // Find the order
        Optional<PendingOrder> orderOptional = orderManager.findPendingOrderById(pendingid);
        // If the order exists, delete it if they have access.
        if (orderOptional.isPresent()) {
            PendingOrder pendingOrder = orderOptional.get();
            orderManager.deleteOrder(pendingOrder.getAccount(), new OrderInfo(pendingOrder.getOrder()));
        }
        // Redirect to the same page.
        return "redirect:" + request.getRequestURI();
    }

    /**
     * @name updateOrder
     *
     * Handles the httppost request to move an order
     * from the user's cart to the pending orders.
     *
     * @param request The request object.
     * @param orderid The order id.
     * @return The redirect URL.
     */
    @PostMapping("/purchase")
    public String updateOrder(
            HttpServletRequest request,
            @RequestParam(value = "orderid", required = true) BigInteger orderid
    ) {
        // Find the order
        Optional<Order> orderOptional = orderManager.findSavedOrderById(orderid);
        // If the order exists, move it to the pending orders.
        if (orderOptional.isPresent())
            orderManager.saveToPendingOrder(orderOptional.get());

        // Redirect to the same page.
        return "redirect:" + request.getRequestURI();
    }

}
