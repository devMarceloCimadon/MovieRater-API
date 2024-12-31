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
import proj.devMarceloCimadon.MovieRater.Dto.Artist.CreateArtistDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Artist;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Repositories.ArtistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {
    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Captor
    private ArgumentCaptor<Artist> artistArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> nameArgumentCaptor;

    @Nested
    class createArtist{
        @Test
        @DisplayName("Should create a artist with success")
        void shouldCreateAArtistWithSuccess(){
            //Arrange
            var artist = new Artist(1L, "name", null);
            doReturn(artist).when(artistRepository).save(artistArgumentCaptor.capture());
            var input = new CreateArtistDto("name");
            //Act
            var output = artistService.createArtist(input);
            //Assert
            assertNotNull(output);
            var artistCaptured = artistArgumentCaptor.getValue();
            assertEquals(input.name(), artistCaptured.getName());
        }

        @Test
        @DisplayName("Should throw a record not created exception")
        void shouldThrowARecordNotCreatedException() {
            //Arrange
            var input = new CreateArtistDto(null);
            //Act & Assert
            assertThrows(RecordNotCreatedException.class, () -> artistService.createArtist(input));
            verify(artistRepository, times(0)).save(any());
        }
    }

    @Nested
    class listArtists{
        @Test
        @DisplayName("Should return all artists with success")
        void shouldReturnAllArtistsWithSuccess() {
            //Arrange
            List<Movie> movies = new ArrayList<>();

            var artist = new Artist(1L, "name", movies);
            var artistList = List.of(artist);

            doReturn(artistList).when(artistRepository).findAll();
            //Act
            var output = artistService.listArtists();
            //Assert
            assertNotNull(output);
            assertEquals(artistList.size(), output.size());
        }
    }

    @Nested
    class findArtistByName{
        @Test
        @DisplayName("Should get a artist by name with success when optional is present")
        void shouldGetAArtistByNameWithSuccessWhenOptionalIsPresent(){
            //Arrange
            var artist = new Artist(1L, "name", null);
            doReturn(Optional.of(artist)).when(artistRepository).findArtistByName(nameArgumentCaptor.capture());
            //Act
            var output = artistService.findArtistByName(artist.getName());
            //Assert
            assertNotNull(output);
            assertEquals(artist.getName(), nameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsPresent(){
            //Arrange
            var artist = new Artist(1L, "name", null);
            doReturn(Optional.empty()).when(artistRepository).findArtistByName(nameArgumentCaptor.capture());
            var artistName = "name";
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> artistService.findArtistByName(artistName));
        }
    }
}