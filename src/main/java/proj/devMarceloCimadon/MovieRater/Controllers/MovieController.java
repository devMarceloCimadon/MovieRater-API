package proj.devMarceloCimadon.MovieRater.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.devMarceloCimadon.MovieRater.Dto.Movie.CreateMovieDto;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Services.MovieService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody CreateMovieDto createMovieDto){
        var movieId = movieService.createMovie(createMovieDto);
        return ResponseEntity.created(URI.create("/v1/movie/" + movieId.toString())).build();
    }

    @GetMapping("/{movieName}")
    public ResponseEntity<Movie> getMovieByName(@PathVariable("movieName") String movieName){
        var movie = movieService.findMovieByName(movieName);
        return ResponseEntity.ok(movie.get());
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> listMovies(){
        var movies = movieService.listMovies();
        return ResponseEntity.ok(movies);
    }
}