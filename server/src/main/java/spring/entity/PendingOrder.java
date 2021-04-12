package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PendingOrder {
    @Id
    @GeneratedValue
    @Column
    private Long pendingOrderNumber;

    @Column
    private String username;

    @ManyToOne
    private Order order;

    public Long getPendingOrderNumber()
    {
        return pendingOrderNumber;
    }

    public void setPendingOrderNumber( Long orderNumber )
    {
        this.pendingOrderNumber = orderNumber;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder( Order order )
    {
        this.order = order;
    }
}
