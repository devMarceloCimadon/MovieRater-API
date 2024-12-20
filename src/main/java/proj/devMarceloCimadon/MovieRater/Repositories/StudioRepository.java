package proj.devMarceloCimadon.MovieRater.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import proj.devMarceloCimadon.MovieRater.Models.Studio;

import java.util.Optional;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long>{
    @Query("SELECT s FROM Studio s WHERE s.name LIKE %:name%")
    public Optional<Studio> findStudioByName(String name);
}
