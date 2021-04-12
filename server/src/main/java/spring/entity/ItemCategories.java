package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ItemCategories {

    @Id
    @Column
    private Long itemID;

    @ManyToOne
    private Category category;

    public Long getItemID()
    {
        return itemID;
    }

    public void setItemID( Long itemID )
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
