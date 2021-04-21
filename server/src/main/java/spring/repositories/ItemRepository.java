package spring.repositories;


import spring.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface ItemRepository extends PagingAndSortingRepository<Item, BigInteger> {
    Optional<Item> findById( BigInteger id );
}
