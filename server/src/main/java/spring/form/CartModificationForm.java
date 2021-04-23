package spring.form;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CartModificationForm {

    private String action = "modify";
    private BigInteger order_id;
    private BigInteger item_id;
    private Integer quantity;
    private BigDecimal price;

    public String getAction()
    {
        return this.action;
    }

    public void setAction( String action )
    {
        this.action = action;
    }

    public BigInteger getOrder_id()
    {
        return order_id;
    }

    public void setOrder_id( BigInteger order_id )
    {
        this.order_id = order_id;
    }

    public BigInteger getItem_id()
    {
        return item_id;
    }

    public void setItem_id( BigInteger item_id )
    {
        this.item_id = item_id;
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
}
