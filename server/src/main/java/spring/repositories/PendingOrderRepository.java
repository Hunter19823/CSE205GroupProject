package spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import spring.entity.PendingOrder;

public interface PendingOrderRepository extends PagingAndSortingRepository<PendingOrder,Long> {

}
