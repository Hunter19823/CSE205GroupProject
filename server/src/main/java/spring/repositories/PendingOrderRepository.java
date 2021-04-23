package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import spring.entity.PendingOrder;

import java.math.BigInteger;
import java.util.Optional;

public interface PendingOrderRepository extends PagingAndSortingRepository<PendingOrder, BigInteger> {

    @Query(
            value = "SELECT * FROM store.pending_orders WHERE store.pending_orders.order_id = :order_id",
            nativeQuery = true
    )
    Optional<PendingOrder> findByOrderId(
            @Param("order_id") BigInteger order_id
    );
}
