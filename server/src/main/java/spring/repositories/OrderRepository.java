package spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
}
