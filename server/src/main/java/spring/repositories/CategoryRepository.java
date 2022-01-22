package spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category,String> {

}
