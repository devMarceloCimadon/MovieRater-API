package proj.devMarceloCimadon.MovieRater.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Models.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface ReviewRepository extends JpaRepository<Review, Long>{
    @Query("SELECT r FROM Review r WHERE r.user.id = :userId")
    Optional<List<Review>> findReviewsByUserId(@Param("userId") UUID id);

    @Query("SELECT r FROM Review r WHERE r.movie.name = :movieName")
    Optional<List<Review>> findReviewsByMovieName(@Param("movieName") String movieName);
}
