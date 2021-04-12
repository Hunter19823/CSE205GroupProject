package spring.form;

import spring.entity.Item;

import java.math.BigDecimal;

public class ItemForm {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;

    public ItemForm()
    {

    }

    public ItemForm( Item item)
    {
        this.id = item.getUuid();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
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

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }
}
