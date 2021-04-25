package spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import spring.entity.Account;

import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, String> {
    @Query(value = "UPDATE users SET accountType = :authority WHERE users.username = :username RETURNING *", nativeQuery = true)
    Optional<Account> updateAuthorityById(@Param("username") String username, @Param("authority") String authority);
}
