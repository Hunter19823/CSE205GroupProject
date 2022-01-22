package spring.model;

import spring.entity.Order;
import spring.entity.PendingOrder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartInfo {
    private final Map<BigInteger, OrderInfo> orderInfoHashMap = new HashMap<>();

    public ShoppingCartInfo()
    {
    }

    public ShoppingCartInfo( List<Order> orderList){
        orderList.parallelStream().forEachOrdered(order ->
                orderInfoHashMap.put(order.getItem().getUuid(),new OrderInfo(order))
        );
    }

    public ShoppingCartInfo( List<Order> orderList, List<PendingOrder> pendingOrdersList){
        orderList.parallelStream().forEachOrdered(order ->
                orderInfoHashMap.put(order.getItem().getUuid(),new OrderInfo(order))
        );
        pendingOrdersList.stream().forEachOrdered( order -> {
            if(orderInfoHashMap.containsKey(order.getOrder().getItem().getUuid())){
                orderInfoHashMap.get(order.getOrder().getItem().getUuid()).setPurchased(true);
            }
        });
    }

    public Map<BigInteger, OrderInfo> getOrderInfoHashMap()
    {
        return orderInfoHashMap;
    }

    public OrderInfo getOrder(BigInteger order_id)
    {
        return orderInfoHashMap.getOrDefault(order_id,new OrderInfo());
    }

    public void putOrder( OrderInfo order)
    {
        orderInfoHashMap.put(order.getItemID(), order);
    }

    public void deleteOrder( BigInteger itemid)
    {
        if(orderInfoHashMap.containsKey(itemid)) {
            orderInfoHashMap.remove(itemid);
        }
    }

    public boolean hasOrder( BigInteger item_id )
    {
        return orderInfoHashMap.containsKey(item_id);
    }

    public boolean isEmpty()
    {
        return this.orderInfoHashMap.isEmpty();
    }

    public Integer getTotalItems()
    {
        return orderInfoHashMap.size();
    }

    public BigDecimal getAmountTotal()
    {
        // TODO
        BigDecimal total = new BigDecimal(0);

        return total;
    }

}
