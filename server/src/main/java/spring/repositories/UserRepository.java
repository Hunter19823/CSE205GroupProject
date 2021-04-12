package spring.repositories;


import spring.entity.Account;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<Account,String> {

}
