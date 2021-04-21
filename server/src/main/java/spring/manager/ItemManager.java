package spring.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.entity.Item;
import spring.entity.ItemCategories;
import spring.repositories.CategoryRepository;
import spring.repositories.ItemCategoryRepository;
import spring.repositories.ItemRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    public Item registerItem( String name, String description, int quantity, BigDecimal price )
    {
        // TODO validate input
        if(name.length() < 50)
            itemRepository.save(new Item(name, description, quantity, price));
        if(name == null)
            throw new IllegalArgumentException("Item must be given a name");
        if(name.length() > 50)
            throw new IllegalArgumentException("Item name is too long");
        if(description.length() < 512)
            itemRepository.save(new Item(name, description, quantity, price));
        if(quantity < 0)
            throw new IllegalArgumentException("Cannot have less than 0 copies of an item");
        if(quantity >= 0)
            itemRepository.save(new Item(name, description, quantity, price));
        if(price.compareTo(price) < 0)
            throw new IllegalArgumentException("Price cannot go below 0");
        if(price.compareTo(price) >= 0)
            itemRepository.save(new Item(name, description, quantity, price));
        return itemRepository.save(new Item(name,description,quantity,price));
    }

    public Page<Item> findAllItems( Pageable pageable )
    {
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> findByItemId( BigInteger id )
    {
        return itemRepository.findById(id);
    }

    public Optional<ItemCategories> findItemCategory( BigInteger id )
    {
        return itemCategoryRepository.findById(id);
    }



}
