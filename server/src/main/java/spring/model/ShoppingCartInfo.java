package spring.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartInfo {
    private Long orderNumber;

    private final Map<Long,ItemInfo> items = new HashMap<>();

    public ShoppingCartInfo()
    {
    }

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( Long orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    public Map<Long,ItemInfo> getItems()
    {
        return items;
    }

    public void addItem( ItemInfo item, int quantity)
    {
        // TODO
    }

    public void updateItem( ItemInfo item, int quantity)
    {
        // TODO
    }

    public void removeItem( ItemInfo item)
    {
        // TODO
    }

    public boolean isEmpty()
    {
        return this.items.isEmpty();
    }

    public Long getTotalItems()
    {
        // TODO
        return 0L;
    }

    public BigDecimal getAmountTotal()
    {
        // TODO
        return null;
    }

}
