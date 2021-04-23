package spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.dao.AccountDAO;
import spring.entity.Account;
import spring.entity.Item;
import spring.entity.Order;
import spring.form.CartModificationForm;
import spring.manager.AccountManager;
import spring.manager.ItemManager;
import spring.manager.OrderManager;
import spring.model.AccountInfo;
import spring.model.OrderInfo;
import spring.model.ShoppingCartInfo;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Optional;

@Controller
@Transactional
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingCartController.class);
    private final ItemManager itemManager;
    private final AccountManager accountManager;
    private final OrderManager orderManager;

    public ShoppingCartController( ItemManager itemManager, AccountManager accountManager, OrderManager orderManager )
    {
        this.itemManager = itemManager;
        this.accountManager = accountManager;
        this.orderManager = orderManager;
    }

    @GetMapping
    public String shoppingCartHandler( Model model,
                                       UsernamePasswordAuthenticationToken authenticationToken
    )
    {
        // TODO
        model.addAttribute("authorized",AccountController.attachUserInfo(model, authenticationToken, orderManager));

        return "cart";
    }

    @PostMapping
    public String shoppingCartPostHandler(
            Model model,
            UsernamePasswordAuthenticationToken authenticationToken,
            CartModificationForm cartModificationForm,
            @RequestParam(value = "categoryid",required = true) String categoryid,
            @RequestParam(value = "itemid", required = true)    BigInteger itemId
            )
    {
        Account account = ((AccountDAO)authenticationToken.getPrincipal()).getAccount();
        ShoppingCartInfo cartInfo = orderManager.loadCart(account);
        OrderInfo order = cartInfo.getOrder(itemId);
        order.setItemID(itemId);
        order.setQuantity(cartModificationForm.getQuantity());
//        LOGGER.info("Action: {}",cartModificationForm.getAction());
//        LOGGER.info("Item ID: {}",cartModificationForm.getItem_id());
//        LOGGER.info("Order ID: {}",cartModificationForm.getOrder_id());
//        LOGGER.info("Quantity: {}",cartModificationForm.getQuantity());
        try {
            if(cartModificationForm == null)
                throw new IllegalArgumentException("CartModification Form cannot be null!");
            OrderInfo orderInfo;
            if(cartModificationForm.getAction().equals("modify")){
                if(cartModificationForm.getQuantity() <= 0){
                    orderManager.deleteOrder(account, order);
                    cartInfo.deleteOrder(cartModificationForm.getItem_id());
                }else{
                    orderInfo = orderManager.upsertOrder(account, order);
                    cartInfo.putOrder(orderInfo);
                }
            }else{
                throw new Exception("Action Not Supported.");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            LOGGER.error(e);
            try {
                return "redirect:/category?error=true&message=" + URLEncoder.encode(e.getMessage() != null ? e.getMessage() : "Unknown Error", "UTF-8") + "&categoryid=" + categoryid;
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error(ex);
            }
        }
        model.addAttribute("cart",cartInfo);
        return "redirect:/category?categoryid=" + categoryid;
    }
}
