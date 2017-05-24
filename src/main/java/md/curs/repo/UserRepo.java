package md.curs.repo;

import md.curs.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by MG
 */
public interface UserRepo extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name like %:query% OR u.email like %:query%")
    List<User> find(@Param("query") String query);
}
