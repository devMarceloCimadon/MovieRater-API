package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Movie.CreateMovieDto;
import proj.devMarceloCimadon.MovieRater.Dto.Movie.ResponseMovieDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Repositories.MovieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final StudioService studioService;
    private final ArtistService artistService;

    public MovieService(MovieRepository movieRepository, StudioService studioService, ArtistService artistService) {
        this.movieRepository = movieRepository;
        this.studioService = studioService;
        this.artistService = artistService;
    }

    public Long createMovie(CreateMovieDto createMovieDto){
        if(createMovieDto.name() == null){
            throw new RecordNotCreatedException();
        }
        var movie = new Movie();
        movie.setName(createMovieDto.name());
        movie.setDescription(createMovieDto.description());
        movie.setStudio(studioService.findStudioByName(createMovieDto.studio()));
        var artists = createMovieDto.cast().stream().map(artistService::findArtistByName).collect(Collectors.toList());
        movie.setArtists(artists);

        var movieSaved = movieRepository.save(movie);

        return movieSaved.getMovieId();
    }

    public List<ResponseMovieDto> listMovies(){
        var movies = movieRepository.findAll();
        return movies.stream().map(ResponseMovieDto :: fromEntity).toList();
    }

    public void updateMovieGrade(String movieName, List<Review> reviews){
        var movie = findMovieByName(movieName);
        var averageGrade = reviews.stream().mapToDouble(Review::getGrade).average().orElse(0.0);
        movie.setFinalGrade((float) averageGrade);

        movieRepository.save(movie);
    }

    public Movie findMovieByName(String name){
        return movieRepository.findMovieByName(name).orElseThrow(() -> new RecordNotFoundException("Movie name", name));
    }
}