package spring.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "item_categories", schema = "store")
public class ItemCategories {

    @Id
    @Column(name = "id")
    private BigInteger itemID;

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
