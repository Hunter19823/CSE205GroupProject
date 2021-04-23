package spring.manager;

import org.springframework.stereotype.Service;
import spring.entity.Account;
import spring.entity.Item;
import spring.entity.Order;
import spring.entity.PendingOrder;
import spring.model.OrderInfo;
import java.math.BigInteger;
import java.nio.file.AccessDeniedException;
import java.util.List;
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

    private final ItemManager itemManager;

    public OrderManager( OrderRepository orderRepository, PendingOrderRepository pendingOrderRepository, ItemManager itemManager )
    {
        this.orderRepository = orderRepository;
        this.pendingOrderRepository = pendingOrderRepository;
        this.itemManager = itemManager;
    }

    public OrderInfo upsertOrder( Account account, OrderInfo itemInfo) throws AccessDeniedException, IllegalArgumentException
    {
        // Null Check
        if(itemInfo.getQuantity() == null)
            throw new IllegalArgumentException("Quantity cannot be null.");
        // Invalid Quantity Check
        if(itemInfo.getQuantity() <= 0)
            throw new IllegalArgumentException("Item quantity cannot be less than or equal to 0");

        // Validate Item
        Optional<Item> itemOptional = itemManager.findItemById(itemInfo.getItemID());
        if(!itemOptional.isPresent())
            throw new IllegalArgumentException("Item does not exist.");
        Item item = itemOptional.get();

        // TODO validate item quantity.



        // Insert if order does not exist.
        Optional<Order> existingOrder;
        Order order;
        // If ID is not given, create a new
        if(itemInfo.getId() == null)
        {
            existingOrder = (insert(
                    account.getUsername(),
                    itemInfo.getItemID(),
                    itemInfo.getQuantity()
            ));
        }else{
            existingOrder = findSavedOrderByItemId(account, itemInfo.getItemID());
            if(existingOrder.isPresent()){
                order = existingOrder.get();

                if(!order.getAccount().getUsername().equals(account.getUsername()))
                    throw new AccessDeniedException("Account does not own this order!");

                if(!itemInfo.getQuantity().equals(order.getQuantity()))
                    existingOrder = update(order.getOrderNumber(), itemInfo.getQuantity());
            }else{
                existingOrder = (insert(
                        account.getUsername(),
                        itemInfo.getItemID(),
                        itemInfo.getQuantity()
                ));
            }
        }
        if(!existingOrder.isPresent())
            throw new Error("Existing Order is not present after upsert. (This should never happen!)");

        order = existingOrder.get();

        return new OrderInfo(order);
    }

    public boolean deleteOrder( Account account, OrderInfo orderInfo ) throws AccessDeniedException, IllegalArgumentException
    {
        Optional<Order> orderOptional;
        if(orderInfo.getId() == null)
            orderRepository.findOrdersByAccountAndItem(account.getUsername(),orderInfo.getItemID());
        orderOptional = findSavedOrderByItemId(account, orderInfo.getItemID());

        if(!orderOptional.isPresent())
            return true;

        Order order = orderOptional.get();
        if(order.getAccount().getUsername().equals(account.getUsername()))
        {
            // Delete any pending orders first, as they depend on the order as well.
            Optional<PendingOrder> pendingOrderOptional = pendingOrderRepository.findByOrderId(order.getOrderNumber());
            if(pendingOrderOptional.isPresent())
                pendingOrderRepository.deleteById(pendingOrderOptional.get().getPendingOrderNumber());
            // Delete the actual order.
            orderRepository.deleteById(order.getOrderNumber());
            return true;
        }else{
            throw new AccessDeniedException("Account does not own this order!");
        }
    }

    private Optional<Order> insert(String username, BigInteger itemId, Integer quantity )
    {
        return orderRepository.insert(username, itemId, quantity);
    }

    private Optional<Order> update(BigInteger order_id, Integer quantity)
    {

        return orderRepository.update(order_id,quantity);
    }

    public Optional<PendingOrder> findPendingOrderByOrderId( BigInteger id )
    {
        if(id == null)
            throw new IllegalArgumentException("Order id cannot be null.");

        return pendingOrderRepository.findByOrderId(id);
    }

    public Optional<Order> findSavedOrderById( BigInteger id )
    {
        if(id == null)
            throw new IllegalArgumentException("Order id cannot be null.");

        return orderRepository.findById(id);
    }

    public Optional<Order> findSavedOrderByItemId(Account account, BigInteger itemID)
    {
        if(itemID == null)
            throw new IllegalArgumentException("ItemID cannot be null");

        Optional<List<Order>> orders = orderRepository.findOrdersBy(account.getUsername());

        if(orders.isPresent()) {
            return orders.get().parallelStream().filter(order -> order.getItem().getUuid().equals(itemID)).findAny();
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Order>> findAllSavedOrdersByAccount( Account account )
    {
        return orderRepository.findOrdersBy(account.getUsername());
    }

    public ShoppingCartInfo loadCart( Account account )
    {
        Optional<List<Order>> orders = findAllSavedOrdersByAccount(account);
        if(orders.isPresent()){
            return new ShoppingCartInfo(orders.get());
        }else {
            return new ShoppingCartInfo();
        }
    }



    /**
     * Save an order to pending orders.
     * Basically once the user is ready to purchase
     * the order, they save the order here.
     *
     * @param order         A valid order.
     * @return              A pending order object.
     */
    private PendingOrder saveToPendingOrder( Order order )
    {
        // TODO check if order is already pending.
        // Using pendingOrderRepository.findByOrderId
        PendingOrder pendingOrder = new PendingOrder();
        pendingOrder.setOrder(order);
        pendingOrder.setAccount(order.getAccount());

        return pendingOrderRepository.save(pendingOrder);
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
