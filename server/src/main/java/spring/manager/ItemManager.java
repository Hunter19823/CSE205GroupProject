package spring.manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import spring.entity.Category;
import spring.entity.Item;
import java.math.BigInteger;
import spring.entity.ItemCategories;
import spring.repositories.CategoryRepository;
import spring.repositories.ItemCategoryRepository;
import spring.repositories.ItemRepository;
import spring.util.SettingUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemManager {
    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemManager( ItemRepository itemRepository, CategoryRepository categoryRepository, ItemCategoryRepository itemCategoryRepository )
    {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public Item registerItem( String name, String description, int quantity, BigDecimal price, String category_id)
    {
        // Null Checks
        if(name == null || name.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Item name cannot be null");
        if(description == null || description.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Description cannot be null");
        if(price == null)
            throw new IllegalArgumentException("Price cannot be null");
        // Length checks
        if(name.length() > SettingUtil.ITEM_NAME_LENGTH)
            throw new IllegalArgumentException("Item name is too long");
        if(description.length() > SettingUtil.ITEM_DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("Item description is too long");

        // Negative checks
        if(quantity < 0)
            throw new IllegalArgumentException("Cannot have less than 0 copies of an item");
        if(price.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException("Price cannot go below 0");


        // All checks passed, saving.
        Item item = itemRepository.save(new Item(name,description,quantity,price));

        // Save to category.
        Optional<Category> categoryOptional = findCategoryById(category_id);
        if(!categoryOptional.isPresent())
            throw new IllegalArgumentException("Invalid Category ID");

        registerItemCategory(item.getUuid(),category_id);

        return item;
    }

    public Category registerCategory( String category_id, String name )
    {
        // Null Checks
        if(category_id == null || category_id.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Category ID name cannot be null");
        if(name == null || name.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Category name cannot be null");

        // Length Checks
        if(name.length() > SettingUtil.CATEGORY_ID_LENGTH)
            throw new IllegalArgumentException("Category ID is too long.");
        if(name.length() > SettingUtil.CATEGORY_NAME_LENGTH)
            throw new IllegalArgumentException("Category name is too long.");

        // Duplicate Checks
        if(categoryRepository.existsById(category_id))
            throw new IllegalArgumentException("Category ID already exists.");

        Category category = new Category();
        category.setCategoryID(category_id);
        category.setCategoryName(name);

        return categoryRepository.save(category);
    }

    public ItemCategories registerItemCategory( BigInteger itemID, String category_id)
    {
        // Existence Check
        Optional<Item> itemOptional = findItemById(itemID);
        if(!itemOptional.isPresent())
            throw new IllegalArgumentException("Item does not exist in database.");

        // Existence Check
        Optional<Category> categoryOptional = findCategoryById(category_id);
        if(!categoryOptional.isPresent())
            throw new IllegalArgumentException("Category does not exist in database.");

        ItemCategories itemCategories = new ItemCategories();
        itemCategories.setItem(itemOptional.get().getUuid());
        itemCategories.setCategory(categoryOptional.get());

        return itemCategoryRepository.save(itemCategories);
    }

    public void updateItem(BigInteger itemID, BigDecimal price, Integer quantity)
    {
        // Null Check
        if(price == null)
            throw new IllegalArgumentException("Price cannot be null");
        if(quantity == null)
            throw new IllegalArgumentException("Price cannot be null");

        // Existence Check
        Optional<Item> itemOptional = findItemById(itemID);
        if(!itemOptional.isPresent())
            throw new IllegalArgumentException("Item does not exist in database.");

        // Negative checks
        if(quantity < 0)
            throw new IllegalArgumentException("Cannot have less than 0 copies of an item");
        if(price.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException("Price cannot go below 0");
        itemRepository.updateItem(itemID,price,quantity);
    }

    public Page<Item> findAllItemsByPageable( Pageable pageable )
    {
        // Null Check
        if(pageable == null)
            throw new IllegalArgumentException("Pageable cannot be null");

        // Query Database
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> findItemById( BigInteger id )
    {
        // Null Check
        if(id == null)
            throw new IllegalArgumentException("Item id cannot be null");

        // Query Database
        return itemRepository.findById(id);
    }

    public Page<BigInteger> findItemIdsInCategory( String categoryId, @PageableDefault(size = 1) Pageable pageable)
    {
        // Existence Check
        Optional<Category> categoryOptional = findCategoryById(categoryId);
        if(!categoryOptional.isPresent())
            throw new IllegalArgumentException("Category ID does not exist.");

        return itemCategoryRepository.findItemsByCategoryId(categoryId, pageable);
    }

    public Optional<Category> findCategoryById( String categoryID )
    {
        // Null Check
        if(categoryID == null || categoryID.equalsIgnoreCase("null"))
            throw new IllegalArgumentException("Category id cannot be null");
        // Length Check
        if(categoryID.length() > SettingUtil.CATEGORY_ID_LENGTH)
            throw new IllegalArgumentException("Category id is too long.");

        // Query Database
        return categoryRepository.findById(categoryID);
    }

    public Optional<ItemCategories> findItemCategoryById( BigInteger id )
    {
        // Null Check
        if(id == null)
            throw new IllegalArgumentException("Category id cannot be null");

        // Query Database
        return itemCategoryRepository.findById(id);
    }

    public Page<Category> findAllCategories( @PageableDefault(size = 5) Pageable pageable)
    {

        return categoryRepository.findAll(pageable);
    }

    public Iterable<Item> findAllItemsInList(Page<BigInteger> integers)
    {
        return itemRepository.findAllById(integers);
    }



}
