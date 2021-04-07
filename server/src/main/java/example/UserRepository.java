package example;


import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserAccount,String> {

}
