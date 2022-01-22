package spring.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Set;

import static spring.util.SettingUtil.CATEGORY_ID_LENGTH;
import static spring.util.SettingUtil.CATEGORY_NAME_LENGTH;

/**
 * @name Category
 *
 * The entity class for category.
 */
@Entity
@Table( name = "categories", schema = "store")
public class Category {
    /**
     * The category id.
     * This is a unique primary key.
     */
    @Id
    @Column(name = "id", length = CATEGORY_ID_LENGTH, nullable = false, unique = true)
    private String categoryID;

    /**
     * The category name.
     */
    @Column(name = "name", length = CATEGORY_NAME_LENGTH, nullable = false)
    private String categoryName;

    /**
     * A set of items associated with this category.
     */
    @OneToMany(mappedBy = "category")
    private Set<ItemCategories> itemCategories;

    public Set<ItemCategories> getItemCategories()
    {
        return itemCategories;
    }

    public void setItemCategories( Set<ItemCategories> itemCategories )
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
