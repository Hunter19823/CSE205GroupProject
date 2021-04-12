package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {
    @Id
    @Column
    private Long categoryID;

    @Column
    private String categoryName;

    public Long getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID( Long categoryID )
    {
        this.categoryID = categoryID;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName( String categoryName )
    {
        this.categoryName = categoryName;
    }
}
