package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.Movie.CreateMovieDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Artist;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Repositories.MovieRepository;

import java.util.ArrayList;
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
        movie.setStudio(studioService.findStudioByName(createMovieDto.studio()).orElseThrow(() -> new RecordNotFoundException("Studio", createMovieDto.studio())));
        var artists = createMovieDto.cast().stream()
                .map(artistName -> artistService.findArtistByName(artistName).orElseThrow(() -> new RecordNotFoundException("Artist", artistName)))
                .collect(Collectors.toList());
        movie.setArtists(artists);

        var movieSaved = movieRepository.save(movie);

        return movieSaved.getMovieId();
    }

    public List<Movie> listMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieByName(String name){
        var movie = movieRepository.findMovieByName(name);
        if (movie.isEmpty()){
            throw new RecordNotFoundException("Name", name);
        }
        return movie;
    }
}