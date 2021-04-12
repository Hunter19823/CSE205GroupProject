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

        return itemRepository.save(new Item(name,description,quantity,price));
    }

    public Page<Item> findAllItems( Pageable pageable )
    {
        // TODO validate input

        return itemRepository.findAll(pageable);
    }

    public Optional<Item> findByItemId( Long id )
    {
        // TODO validate input

        return itemRepository.findById(id);
    }

    public Optional<ItemCategories> findItemCategory( Long id )
    {
        // TODO validate input

        return itemCategoryRepository.findById(id);
    }



}
