package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.entity.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
    @Query( value = "SELECT * FROM Order WHERE username = :user;",
            nativeQuery = true)
    List<Order> findOrdersBy( @Param("user") String username);

    @Query(value = "UPDATE store.saved_orders SET quantity = :quantity, item_id = :item_id WHERE saved_orders.username = :username AND saved_orders.item_id = :item_id\n" +
            "RETURNING *;\n" +
            "INSERT INTO store.saved_orders(username, item_id, quantity)\n" +
            "    SELECT :username, :item_id, :quantity\n" +
            "    WHERE NOT EXISTS( SELECT 1 FROM store.saved_orders where saved_orders.username = username AND saved_orders.item_id = item_id)\n" +
            "RETURNING *;",
            nativeQuery = true)
    Order upsert(
            @Param("username") String username,
            @Param("item_id") BigInteger itemId,
            @Param("quantity") Integer quantity
        );

    @Query(value = "UPDATE store.saved_orders SET quantity = :quantity, item_id = :item_id WHERE saved_orders.username = :username AND saved_orders.item_id = :item_id\n" +
            "RETURNING *",
            nativeQuery = true)
    Optional<Order> update(
            @Param("username") String username,
            @Param("item_id") BigInteger itemId,
            @Param("quantity") Integer quantity
    );

    @Query(value = "INSERT INTO store.saved_orders(username, item_id, quantity)\n" +
            "    SELECT :username, :item_id, :quantity\n" +
            "    WHERE NOT EXISTS( SELECT 1 FROM store.saved_orders where saved_orders.username = username AND saved_orders.item_id = item_id)\n" +
            "RETURNING *",
            nativeQuery = true)
    Optional<Order> insert(
            @Param("username") String username,
            @Param("item_id") BigInteger itemId,
            @Param("quantity") Integer quantity
    );

    @Query(value = "DELETE FROM store.saved_orders WHERE saved_orders.username = :username AND saved_orders.item_id = :item_id",
    nativeQuery = true)
    void removeByItemId(
            @Param("username") String username,
            @Param("item_id") Long orderId
    );


}
