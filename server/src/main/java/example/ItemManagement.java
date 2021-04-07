package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class ItemManagement {

    // Yes it looks like an error, but @RequiredArgsConstructor actually fixes this.
    @Autowired
    ItemRepository itemRepository;

    public ItemManagement()
    {

    }


    public Item registerItem( String name, String description, int quantity, BigDecimal price )
    {
        Assert.notNull(name, "Name must not be null!");
        Assert.notNull(description, "Description must not be null!");
        Assert.notNull(quantity, "Quantity must not be null!");
        Assert.notNull(price, "Price must not be null!");

        return itemRepository.save(new Item(name,description,quantity,price));
    }

    public Page<Item> findAll( Pageable pageable )
    {
        Assert.notNull(pageable,"Pageable must not be null!");

        return itemRepository.findAll(pageable);
    }

    public Optional<Item> findById( Long id )
    {
        Assert.notNull(id,"Pageable must not be null!");

        return itemRepository.findById(id);
    }


}
