package recipes.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    //Find a specific user using email
    Optional<UserModel> findByEmail(String email);
}
