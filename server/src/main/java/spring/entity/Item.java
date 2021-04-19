package spring.entity;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity // https://www.objectdb.com/api/java/jpa/Entity
@Table(name = "ITEMS", schema="store") // https://www.objectdb.com/api/java/jpa/Table
public class Item {
    private static final Logger LOGGER = LogManager.getLogger(Item.class);

    @Id// https://www.objectdb.com/api/java/jpa/Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UUID", nullable = false, unique = true)
    private Long uuid;

    @Column(name = "ITEM_NAME", length = 50, nullable = false, unique = false) // https://www.objectdb.com/api/java/jpa/Column
    private String name;

    @Column(name = "DESCRIPTION", length = 512, nullable = false, unique = false)
    @Lob // https://www.objectdb.com/api/java/jpa/Lob
    private String description;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

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

    public Long getUuid()
    {
        return this.uuid;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public BigDecimal getPrice()
    {
        return this.price;
    }

    public void setUuid( Long uuid )
    {
        this.uuid = uuid;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public void setQuantity( int quantity )
    {
        this.quantity = quantity;
    }

    public void setPrice( BigDecimal price )
    {
        this.price = price;
    }
}
