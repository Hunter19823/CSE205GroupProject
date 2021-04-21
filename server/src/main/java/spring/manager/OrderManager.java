package spring.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Order;
import spring.model.OrderInfo;
import spring.model.ShoppingCartInfo;
import spring.repositories.OrderRepository;
import spring.repositories.PendingOrderRepository;

import javax.transaction.Transactional;

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

    /*private Order upsert(Long orderId, int quantity, Long itemid)
    {

    }*/

    private OrderInfo saveToOrder(OrderInfo itemInfo)
    {
        //TODO validate shopping cart info.
        //if(!orderRepository.existsById(saveToOrder(itemInfo)))
            //orderRepository.upsert(orderId, quantity, itemid);

        //TODO create order from valid shopping cart info

        Order order = new Order();
        order = orderRepository.save(order);
        return itemInfo;
    }

    /*public Long saveToCart(ShoppingCartInfo shoppingCartInfo)
    {

    }

    public ShoppingCartInfo loadCart(String username)
    {

    }*/
}
