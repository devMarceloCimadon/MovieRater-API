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
import proj.devMarceloCimadon.MovieRater.Dto.Studio.CreateStudioDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Models.Studio;
import proj.devMarceloCimadon.MovieRater.Repositories.StudioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudioServiceTest {
    @Mock
    private StudioRepository studioRepository;

    @InjectMocks
    private StudioService studioService;

    @Captor
    private ArgumentCaptor<Studio> studioArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> nameArgumentCaptor;

    @Nested
    class createStudio{
        @Test
        @DisplayName("Should create a studio with success")
        void shouldCreateAStudioWithSuccess(){
            //Arrange
            var studio = new Studio(1L, "name", null);
            doReturn(studio).when(studioRepository).save(studioArgumentCaptor.capture());
            var input = new CreateStudioDto("name");
            //Act
            var output = studioService.createStudio(input);
            //Assert
            assertNotNull(output);
            var studioCaptured = studioArgumentCaptor.getValue();
            assertEquals(input.name(), studioCaptured.getName());
        }

        @Test
        @DisplayName("Should throw a record not created exception")
        void shouldThrowARecordNotCreatedException() {
            //Arrange
            var input = new CreateStudioDto(null);
            //Act & Assert
            assertThrows(RecordNotCreatedException.class, () -> studioService.createStudio(input));
            verify(studioRepository, times(0)).save(any());
        }
    }
    
    @Nested
    class listStudios{
        @Test
        @DisplayName("Should return all studios with success")
        void shouldReturnAllStudiosWithSuccess() {
            //Arrange
            List<Movie> movies = new ArrayList<Movie>();
            var studio = new Studio(1L, "name", movies);
            var studioList = List.of(studio);
            doReturn(studioList).when(studioRepository).findAll();
            //Act
            var output = studioService.listStudios();
            //Assert
            assertNotNull(output);
            assertEquals(studioList.size(), output.size());
        }
    }

    @Nested
    class findStudioByName{
        @Test
        @DisplayName("Should get a studio by name with success when optional is present")
        void shouldGetAStudioByNameWithSuccessWhenOptionalIsPresent(){
            //Arrange
            var studio = new Studio(1L, "name", null);
            doReturn(Optional.of(studio)).when(studioRepository).findStudioByName(nameArgumentCaptor.capture());
            //Act
            var output = studioService.findStudioByName(studio.getName());
            //Assert
            assertNotNull(output);
            assertEquals(studio.getName(), nameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsPresent(){
            //Arrange
            var studio = new Studio(1L, "name", null);
            doReturn(Optional.empty()).when(studioRepository).findStudioByName(nameArgumentCaptor.capture());
            var studioName = "name";
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> studioService.findStudioByName(studioName));
        }
    }
}