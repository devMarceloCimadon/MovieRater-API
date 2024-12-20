package proj.devMarceloCimadon.MovieRater.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "studio_id",referencedColumnName = "studio_id")
    private Studio studio;

    @ManyToMany(mappedBy = "watchedMovies")
    private List<User> usersWatched;
    @ManyToMany(mappedBy = "watchList")
    private List<User> usersWatchList;

    @ManyToMany
    @JoinTable(
            name = "movies_cast",
            joinColumns = { @JoinColumn(name = "movie_id", referencedColumnName = "movie_id") },
            inverseJoinColumns = { @JoinColumn(name = "artist_id", referencedColumnName = "artist_id") }
    )
    private List<Artist> artists;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @Column(name = "final_grade")
    private float finalGrade;

    public Movie() {
    }

    public Movie(Long movieId, String name, String description, Studio studio, List<User> usersWatched, List<User> usersWatchList, List<Artist> artists, List<Review> reviews, float finalGrade) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.studio = studio;
        this.usersWatched = usersWatched;
        this.usersWatchList = usersWatchList;
        this.artists = artists;
        this.reviews = reviews;
        this.finalGrade = finalGrade;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public List<User> getUsersWatched() {
        return usersWatched;
    }

    public void setUsersWatched(List<User> usersWatched) {
        this.usersWatched = usersWatched;
    }

    public List<User> getUsersWatchList() {
        return usersWatchList;
    }

    public void setUsersWatchList(List<User> usersWatchList) {
        this.usersWatchList = usersWatchList;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(float finalGrade) {
        this.finalGrade = finalGrade;
    }
}
