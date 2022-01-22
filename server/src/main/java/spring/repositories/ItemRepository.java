package spring.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.entity.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public interface ItemRepository extends PagingAndSortingRepository<Item, BigInteger> {
    Optional<Item> findById( BigInteger id );

    @Query(
            value = "UPDATE store.items SET quantity = :quantity, price = :price WHERE store.items.uuid = :id RETURNING *",
            nativeQuery = true
    )
    Optional<Item> updateItem(
            @Param("id") BigInteger item_id,
            @Param("price") BigDecimal bigDecimal,
            @Param("quantity") Integer quantity
            );
//    @Query(
//            value = "UPDATE store.items"+
//                    " WHERE store.items.quantity = :quantity AND :store.items.uuid = :itemid",
//            nativeQuery = true)
//    void updateItem(
//            @Param("id") BigInteger item_id,
//            @Param("quantity") Integer quantity
//    );

}
