package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "saved_orders", schema = "store")
public class Order {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue
    private BigInteger orderNumber;

    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private Account account;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "uuid", nullable = false)
    private Item itemId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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

    public Item getItemId()
    {
        return itemId;
    }

    public void setItemId( Item itemId )
    {
        this.itemId = itemId;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }


}
