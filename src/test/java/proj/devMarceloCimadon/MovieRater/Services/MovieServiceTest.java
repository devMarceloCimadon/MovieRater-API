package proj.devMarceloCimadon.MovieRater.Services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import proj.devMarceloCimadon.MovieRater.Dto.Movie.CreateMovieDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Artist;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Models.Studio;
import proj.devMarceloCimadon.MovieRater.Repositories.MovieRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Mock
    private StudioService studioService;

    @Mock
    private ArtistService artistService;

    @Captor
    private ArgumentCaptor<Movie> movieArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> nameArgumentCaptor;

    @Nested
    class createMovie {
        @Test
        @DisplayName("Should create a movie with success")
        void shouldCreateAMovieWithSuccess() {
            //Arrange
            var studio = new Studio(1L, "name", null);
            when(studioService.findStudioByName(studio.getName())).thenReturn(studio);

            var artist1 = new Artist(1L, "name1", null);
            var artist2 = new Artist(2L, "name2", null);
            when(artistService.findArtistByName(artist1.getName())).thenReturn(artist1);
            when(artistService.findArtistByName(artist2.getName())).thenReturn(artist2);

            var cast = new ArrayList<String>();
            cast.add(artist1.getName());
            cast.add(artist2.getName());

            var movie = new Movie(1L, "name", "description", studio, null, null, null, null, 7.8F);
            doReturn(movie).when(movieRepository).save(movieArgumentCaptor.capture());

            var input = new CreateMovieDto("name", "description", "name", cast);
            //Act
            var output = movieService.createMovie(input);
            //Assert
            assertNotNull(output);
            var movieCaptured = movieArgumentCaptor.getValue();
            assertEquals(input.name(), movieCaptured.getName());
            assertEquals(input.description(), movieCaptured.getDescription());
            assertEquals(studio, movieCaptured.getStudio());
            assertEquals(cast.size(), movieCaptured.getArtists().size());
            assertTrue(movieCaptured.getArtists().contains(artist1));
            assertTrue(movieCaptured.getArtists().contains(artist2));
        }

        @Test
        @DisplayName("Should throw a record not created exception")
        void shouldThrowARecordNotCreatedException() {
            //Arrange
            var input = new CreateMovieDto(null, null, null, null);
            //Act & Assert
            assertThrows(RecordNotCreatedException.class, () -> movieService.createMovie(input));
            verify(movieRepository, times(0)).save(any());
        }
    }

    @Nested
    class listMovies {
        @Test
        @DisplayName("Should return all movies with success")
        void shouldReturnAllMoviesWithSuccess() {
            //Arrange
            List<Artist> artists = new ArrayList<>();
            artists.add(new Artist(1L, "name", null));

            var studio = new Studio(1L, "name", null);

            var movie = new Movie(1L, "name", "description", studio, null, null, artists, null, 7.8F);
            var movieList = List.of(movie);
            doReturn(movieList).when(movieRepository).findAll();
            //Act
            var output = movieService.listMovies();
            //Assert
            assertNotNull(output);
            assertEquals(movieList.size(), output.size());
        }
    }

    @Nested
    class updateMovieGrade{
        @Test
        @DisplayName("Should update a movie grade with success")
        void shouldUpdateAMovieGradeWithSuccess(){
            //Arrange
            var reviews = new ArrayList<Review>();
            var movie = new Movie(1L, "name", "description", null, null, null, null, reviews, 0.0F);
            reviews.add(new Review(1L, null, movie, 5.5, "content", Instant.now(), null));
            reviews.add(new Review(2L, null, movie, 7.0, "content", Instant.now(), null));
            doReturn(Optional.of(movie)).when(movieRepository).findMovieByName(nameArgumentCaptor.capture());
            doReturn(movie).when(movieRepository).save(movieArgumentCaptor.capture());
            //Act
            movieService.updateMovieGrade(movie.getName(), reviews);
            //Assert
            assertEquals(movie.getName(), nameArgumentCaptor.getValue());

            var movieCaptured = movieArgumentCaptor.getValue();

            assertEquals((float) reviews.stream().mapToDouble(Review::getGrade).average().orElse(0.0), movieCaptured.getFinalGrade());

            verify(movieRepository, times(1)).findMovieByName(nameArgumentCaptor.getValue());
            verify(movieRepository, times(1)).save(movieArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throe a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsEmpty(){

        }
    }

    @Nested
    class findMovieByName {
        @Test
        @DisplayName("Should get a movie by name with success when optional is present")
        void shouldGetAMovieByNameWithSuccessWhenOptionalIsPresent() {
            //Arrange
            var studio = new Studio(1L, "name", null);
            var movie = new Movie(1L, "name", "description", studio, null, null, null, null, 8.0F);
            doReturn(Optional.of(movie)).when(movieRepository).findMovieByName(nameArgumentCaptor.capture());
            //Act
            var output = movieService.findMovieByName(movie.getName());
            //Assert
            assertNotNull(output);
            assertEquals(movie.getName(), nameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsEmpty() {
            //Arrange
            var userName = "Name";
            doReturn(Optional.empty()).when(movieRepository).findMovieByName(nameArgumentCaptor.capture());
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> movieService.findMovieByName(userName));
        }
    }
}
