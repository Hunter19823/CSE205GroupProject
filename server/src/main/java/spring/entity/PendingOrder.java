package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "pending_orders", schema = "store")
public class PendingOrder {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private BigInteger pendingOrderNumber;

    @ManyToOne
    @JoinColumn( name = "username", referencedColumnName = "username", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn( name = "order_id", referencedColumnName = "id", nullable = false, unique = true)
    private Order order;

    public BigInteger getPendingOrderNumber()
    {
        return pendingOrderNumber;
    }

    public void setPendingOrderNumber( BigInteger orderNumber )
    {
        this.pendingOrderNumber = orderNumber;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder( Order order )
    {
        this.order = order;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount( Account account )
    {
        this.account = account;
    }
}
