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
public interface OrderRepository extends PagingAndSortingRepository<Order,BigInteger> {
    @Query( value = "SELECT * FROM Store.saved_orders WHERE store.saved_orders.username = :user",
            nativeQuery = true
    )
    Optional<List<Order>> findOrdersBy( @Param("user") String username);

    @Query( value = "SELECT * FROM Store.saved_orders WHERE store.saved_orders.username = :user AND store.saved_orders.item_id = :itemid",
            nativeQuery = true
    )
    Optional<Order> findOrdersByAccountAndItem(
            @Param("user") String username,
            @Param("itemid") BigInteger itemID
    );

    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM store.saved_orders WHERE store.saved_orders.username = :user)",
            nativeQuery = true
    )
    Boolean orderExistsBy(
            @Param("user") String username
    );

//    @Query(value = "UPDATE store.saved_orders SET quantity = :quantity, item_id = :item_id WHERE saved_orders.username = :username AND saved_orders.item_id = :item_id\n" +
//            "RETURNING *;\n" +
//            "INSERT INTO store.saved_orders(username, item_id, quantity)\n" +
//            "    SELECT :username, :item_id, :quantity\n" +
//            "    WHERE NOT EXISTS( SELECT 1 FROM store.saved_orders where saved_orders.username = username AND saved_orders.item_id = item_id)\n" +
//            "RETURNING *;",
//            nativeQuery = true)
//    Order upsert(
//            @Param("username") String username,
//            @Param("item_id") BigInteger itemId,
//            @Param("quantity") Integer quantity
//        );

    @Query(value = "UPDATE store.saved_orders SET quantity = :quantity, item_id = :item_id WHERE saved_orders.username = :username AND saved_orders.item_id = :item_id\n" +
            "RETURNING *;",
            nativeQuery = true)
    Order update(
            @Param("username") String username,
            @Param("item_id") BigInteger itemId,
            @Param("quantity") Integer quantity
    );

    @Query(value = "UPDATE store.saved_orders SET quantity = :quantity WHERE saved_orders.id = :order_id \n" +
            "RETURNING *;",
            nativeQuery = true)
    Optional<Order> update(
            @Param("order_id") BigInteger id,
            @Param("quantity") Integer quantity
    );

    @Query(value = "INSERT INTO store.saved_orders(username, item_id, quantity)\n" +
            "    SELECT :username, :item_id, :quantity\n" +
            "    WHERE NOT EXISTS( SELECT 1 FROM store.saved_orders where saved_orders.username = :username AND saved_orders.item_id = :item_id)\n" +
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
            @Param("item_id") BigInteger orderId
    );

    @Query(value = "SELECT * FROM store.saved_orders WHERE saved_orders.item_id = :item_id",
    nativeQuery = true)
    Optional<List<Order>> findAllPendingOrdersByItemID(
            @Param("item_id") BigInteger itemId
    );

    @Query(value = "DELETE FROM store.saved_orders WHERE saved_orders.item_id = :item_id RETURNING *",
    nativeQuery = true)
    Optional<List<Order>> deleteAllByItemID(
            @Param("item_id") BigInteger itemId
    );


}
