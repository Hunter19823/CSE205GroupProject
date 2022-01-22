package spring.model;

import spring.entity.Item;
import spring.form.CartModificationForm;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ItemModel {
    private BigInteger uuid;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private String imageURL;
    private CartModificationForm modificationForm;

    public ItemModel( Item item )
    {
        this.uuid = item.getUuid();
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.imageURL = item.getImageURL();
        this.modificationForm = new CartModificationForm();
    }

    public CartModificationForm getModificationForm()
    {
        return modificationForm;
    }

    public void setModificationForm( CartModificationForm modificationForm )
    {
        this.modificationForm = modificationForm;
    }

    public BigInteger getUuid()
    {
        return uuid;
    }

    public void setUuid( BigInteger uuid )
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
