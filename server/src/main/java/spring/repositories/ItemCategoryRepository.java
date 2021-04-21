package spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entity.ItemCategories;

import java.math.BigInteger;
import java.util.Optional;

public interface ItemCategoryRepository extends PagingAndSortingRepository<ItemCategories,BigInteger> {
    Optional<ItemCategories> findById( BigInteger id );
}
