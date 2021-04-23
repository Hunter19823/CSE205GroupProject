package spring.form;


import java.math.BigDecimal;
import java.math.BigInteger;

public class ItemSubmissionForm {
    private BigInteger id;
    private String name = "Name";
    private String description  = "Description";
    private BigDecimal price = BigDecimal.ONE;
    private Integer stock = 1;

    public ItemSubmissionForm()
    {
    }

    public BigInteger getId()
    {
        return id;
    }

    public void setId( BigInteger id )
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

    public Integer getStock()
    {
        return stock;
    }

    public void setStock( Integer stock )
    {
        this.stock = stock;
    }
}
