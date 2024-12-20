package proj.devMarceloCimadon.MovieRater.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_studio")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id")
    private Long studioId;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "studio")
    private List<Movie> movies;

    public Studio() {
    }

    public Studio(Long studioId, String name, List<Movie> movies) {
        this.studioId = studioId;
        this.name = name;
        this.movies = movies;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
