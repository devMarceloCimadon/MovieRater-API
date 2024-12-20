package proj.devMarceloCimadon.MovieRater.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import proj.devMarceloCimadon.MovieRater.Models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    boolean existsByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    Optional<List<User>> findUserByName(@Param("name") String name);
    Optional<User> findUserByUsername(String username);
}
