package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * @name PendingOrder
 *
 * This entity class represents the Pending Orders table.
 */
@Entity
@Table(name = "pending_orders", schema = "store")
public class PendingOrder {
    /**
     * The generated id of the pending order.
     * It is the unique primary key of the table.
     */
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private BigInteger pendingOrderNumber;

    /**
     * The account associated with the pending order.
     */
    @ManyToOne
    @JoinColumn( name = "username", referencedColumnName = "username", nullable = false)
    private Account account;

    /**
     * The order that is pending.
     */
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
