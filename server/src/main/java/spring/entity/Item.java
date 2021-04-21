package spring.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static spring.util.SettingUtil.ITEM_DESCRIPTION_LENGTH;
import static spring.util.SettingUtil.ITEM_NAME_LENGTH;

@Entity
@Table(name = "items", schema = "store")
public class Item {

    @Id
    @GeneratedValue(generator = "items_uuid_seq")
    @Column(name = "uuid", nullable = false, unique = true)
    private BigInteger uuid;

    @Column(name = "name", length = ITEM_NAME_LENGTH, nullable = false)
    private String name;

    @Column(name = "description", length = ITEM_DESCRIPTION_LENGTH, nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @OneToOne(mappedBy = "itemId")
    private Order order;

    @OneToOne(mappedBy = "order")
    private PendingOrder pendingOrder;

    public Item() {
        this.name = null;
        this.description = null;
        this.quantity = 0;
        this.price = null;
    }

    public Item(String name, String description, int quantity, BigDecimal price)
    {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity( int quantity )
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

    public Order getOrder()
    {
        return order;
    }

    public void setOrder( Order order )
    {
        this.order = order;
    }

    public PendingOrder getPendingOrder()
    {
        return pendingOrder;
    }

    public void setPendingOrder( PendingOrder pendingOrder )
    {
        this.pendingOrder = pendingOrder;
    }
}
