package spring.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderInfo {
    private BigInteger id;

    private BigInteger itemID;
    private String itemName;

    private BigDecimal price;
    private Integer quantity;

    public OrderInfo()
    {

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
}
