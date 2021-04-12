package spring.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.entity.Account;

public interface AccountRepository extends CrudRepository<Account, String> {

}
