package spring.manager;

import org.springframework.stereotype.Service;
import spring.entity.Order;
import spring.model.OrderInfo;
import spring.model.ShoppingCartInfo;
import spring.repositories.OrderRepository;
import spring.repositories.PendingOrderRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Transactional
@Service
public class OrderManager {
    private final OrderRepository orderRepository;

    private final PendingOrderRepository pendingOrderRepository;

    public OrderManager( OrderRepository orderRepository, PendingOrderRepository pendingOrderRepository )
    {
        this.orderRepository = orderRepository;
        this.pendingOrderRepository = pendingOrderRepository;
    }

    private BigInteger saveToOrder( OrderInfo orderInfo )
    {
        // TODO validate shopping cart info.

        // TODO create order from valid shopping cart info.
        Order order = new Order();


        order = orderRepository.save(order);

        return order.getOrderNumber();
    }

    public void saveToCart( ShoppingCartInfo shoppingCartInfo){


    }

    public void updateCart( ShoppingCartInfo shoppingCartInfo )
    {

    }

    public boolean cartExists( String username )
    {
        // TODO query if cart exists.

        return false;
    }

    public ShoppingCartInfo loadCart( String username )
    {
        ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();


        try {
            List<Order> orderList = orderRepository.findOrdersBy(username);
        }catch(IllegalArgumentException e)
        {

        }

        // TODO add items to cart.

        return shoppingCartInfo;
    }


}
