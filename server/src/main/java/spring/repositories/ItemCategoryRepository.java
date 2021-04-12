package spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entity.ItemCategories;

import java.util.Optional;

public interface ItemCategoryRepository extends PagingAndSortingRepository<ItemCategories,Long> {
    Optional<ItemCategories> findById( Long id );
}
