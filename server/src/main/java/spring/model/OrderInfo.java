package spring.model;

import spring.entity.Item;
import spring.entity.Order;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderInfo {
    private BigInteger id;

    private BigInteger itemID;
    private String itemName;
    private String itemDescription;

    private BigDecimal price;
    private Integer quantity;

    private Boolean isPurchased;

    public OrderInfo()
    {

    }


    public OrderInfo( Item item, Integer quantity)
    {
        this.itemID = item.getUuid();
        this.itemName = item.getName();
        this.price = item.getPrice();
        this.quantity = quantity;
        this.itemDescription = item.getDescription();
    }
    public OrderInfo( Order order )
    {
        this.id = order.getOrderNumber();
        this.itemID = order.getItem().getUuid();
        this.itemName = order.getItem().getName();
        this.price = order.getItem().getPrice();
        this.quantity = order.getQuantity();
        this.itemDescription = order.getItem().getDescription();
    }

    public OrderInfo( BigInteger id, BigInteger itemID, String itemName, BigDecimal price, Integer quantity )
    {
        this.id = id;
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public BigInteger getId()
    {
        return id;
    }

    public void setId( BigInteger id )
    {
        this.id = id;
    }

    public BigInteger getItemID()
    {
        return itemID;
    }

    public void setItemID( BigInteger itemID )
    {
        this.itemID = itemID;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName( String itemName )
    {
        this.itemName = itemName;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setItemDescription( String itemDescription )
    {
        this.itemDescription = itemDescription;
    }

    public Boolean getPurchased()
    {
        return isPurchased;
    }

    public void setPurchased( Boolean purchased )
    {
        isPurchased = purchased;
    }
}
