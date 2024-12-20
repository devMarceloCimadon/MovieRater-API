package proj.devMarceloCimadon.MovieRater.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proj.devMarceloCimadon.MovieRater.Models.Artist;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>{
    @Query("SELECT a FROM Artist a WHERE a.name LIKE %:name%")
    Optional<Artist> findArtistByName(String name);
}
