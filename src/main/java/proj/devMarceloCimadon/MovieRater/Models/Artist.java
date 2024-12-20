package proj.devMarceloCimadon.MovieRater.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "artists")
    private List<Movie> movies;

    public Artist() {
    }

    public Artist(Long artistId, String name, List<Movie> movies) {
        this.artistId = artistId;
        this.name = name;
        this.movies = movies;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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
