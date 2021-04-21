package spring.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Order;
import spring.entity.PendingOrder;
import spring.model.OrderInfo;
import java.math.BigInteger;
import java.util.Optional;
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

    private Optional<Order> update(String username, BigInteger itemID, Integer quantity)
    {
        return (orderRepository.update(username, itemID, quantity));
    }

    private OrderInfo saveToOrder(OrderInfo itemInfo)
    {
        //TODO validate shopping cart info.
//        if(!orderRepository.existsById(saveToOrder(itemInfo)))
//            orderRepository.update(, quantity, itemid);

        //TODO create order from valid shopping cart info
        if(!orderRepository.existsById(itemInfo.getId().longValue()))
        {

        }
        Order order = new Order();
        order = orderRepository.save(order);
        return itemInfo;
    }
    /*
    This method is to save an order rather than saving an item to an order. So we're getting the order itself and
    saving that potential order. To do this, PendingOrder is of course needed as PendingOrder basically is an order
    that is pending if it is valid or invalid. So we need a PendingOrder object, and save that order into a pending order
    repository.
     */
    private PendingOrder saveOrder(Order item)
    {
//        if(item.getItemId())
//        {
//
//        }
        PendingOrder pendOrder = new PendingOrder();
        pendOrder.setOrder(item);
        return pendOrder;
    }

//    public Long saveToCart(ShoppingCartInfo shoppingCartInfo)
//    {
//
//    }
//
//    public ShoppingCartInfo loadCart(String username)
//    {
//
//    }
}
