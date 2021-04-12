package spring.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    // TODO save Order


}
