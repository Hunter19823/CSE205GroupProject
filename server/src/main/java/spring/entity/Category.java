package spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static spring.util.SettingUtil.CATEGORY_ID_LENGTH;
import static spring.util.SettingUtil.CATEGORY_NAME_LENGTH;

@Entity
@Table( name = "categories", schema = "store")
public class Category {
    @Id
    @Column(name = "id", length = CATEGORY_ID_LENGTH, nullable = false, unique = true)
    private String categoryID;

    @Column(name = "name", length = CATEGORY_NAME_LENGTH, nullable = false)
    private String categoryName;

    @OneToOne(mappedBy = "category")
    private ItemCategories itemCategories;

    public ItemCategories getItemCategories()
    {
        return itemCategories;
    }

    public void setItemCategories( ItemCategories itemCategories )
    {
        this.itemCategories = itemCategories;
    }

    public String getCategoryID()
    {
        return categoryID;
    }

    public void setCategoryID( String categoryID )
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
