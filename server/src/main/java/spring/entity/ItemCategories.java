package spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * @name ItemCategories
 *
 * The entity class for the ItemCategories table.
 * This class is used to associate an Item with a Category.
 */
@Entity
@Table(name = "item_categories", schema = "store")
public class ItemCategories {
    /**
     * The item id.
     * This is the primary key.
     */
    @Id
    @Column(name = "id")
    private BigInteger itemID;

    /**
     * The Associated Category the item is a part of.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "category_id", referencedColumnName = "id")
    private Category category;

    public BigInteger getItem()
    {
        return itemID;
    }

    public void setItem( BigInteger itemID )
    {
        this.itemID = itemID;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory( Category category )
    {
        this.category = category;
    }
}
