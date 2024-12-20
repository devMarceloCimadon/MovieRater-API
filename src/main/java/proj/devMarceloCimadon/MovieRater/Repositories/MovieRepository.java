package proj.devMarceloCimadon.MovieRater.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proj.devMarceloCimadon.MovieRater.Models.Movie;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
    @Query("SELECT m FROM Movie m WHERE m.name LIKE %:name%")
    public Optional<Movie> findMovieByName(String name);
}
