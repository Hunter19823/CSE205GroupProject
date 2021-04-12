package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Order {

    @Id
    @Column
    @GeneratedValue
    private Long orderNumber;

    @Column
    private String username;

    @Column
    private Long itemId;

    @Column
    private int quantity;

    public Long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( Long orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public Long getItemId()
    {
        return itemId;
    }

    public void setItemId( Long itemId )
    {
        this.itemId = itemId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity( int quantity )
    {
        this.quantity = quantity;
    }
}
