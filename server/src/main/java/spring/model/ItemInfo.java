package spring.model;

import java.math.BigDecimal;

public class ItemInfo {
    private Long id;

    private String itemID;
    private String itemName;

    private BigDecimal price;
    private Long quantity;

    public ItemInfo()
    {

    }

    public ItemInfo( Long id, String itemID, String itemName, BigDecimal price, Long quantity )
    {
        this.id = id;
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public String getItemID()
    {
        return itemID;
    }

    public void setItemID( String itemID )
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

    public Long getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Long quantity )
    {
        this.quantity = quantity;
    }
}
