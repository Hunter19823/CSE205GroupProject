package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Set;

/**
 * @name Order
 *
 * The entity class for the Saved Orders table.
 */
@Entity
@Table(name = "saved_orders", schema = "store")
public class Order {
    /**
     * The order id.
     * This is a unique primary key.
     * Randomly generated.
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue
    private BigInteger orderNumber;

    /**
     * The account associated with the order.
     */
    @ManyToOne( optional = false )
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private Account account;

    /**
     * The item in the order.
     */
    @ManyToOne( optional = false )
    @JoinColumn(name = "item_id", referencedColumnName = "uuid", nullable = false)
    private Item item;

    /**
     * The quantity of the item in the order.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    /**
     * The associated pending order.
     */
    @OneToMany(mappedBy = "order" )
    private Set<PendingOrder> pendingOrders;


    public BigInteger getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( BigInteger orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount( Account account )
    {
        this.account = account;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem( Item itemId )
    {
        this.item = itemId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    public Set<PendingOrder> getPendingOrders()
    {
        return pendingOrders;
    }

    public void setPendingOrders( Set<PendingOrder> pendingOrders )
    {
        this.pendingOrders = pendingOrders;
    }
}
