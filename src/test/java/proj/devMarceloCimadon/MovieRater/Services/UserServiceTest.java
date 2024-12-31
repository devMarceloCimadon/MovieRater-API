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
import proj.devMarceloCimadon.MovieRater.Dto.User.CreateUserDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.UpdateUserDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.DataWithThisValueAlreadyExists;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.Movie;
import proj.devMarceloCimadon.MovieRater.Models.Review;
import proj.devMarceloCimadon.MovieRater.Models.User;
import proj.devMarceloCimadon.MovieRater.Repositories.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> nameArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {
            //Arrange
            var user = new User(UUID.randomUUID(), "username", "name", "email@email.com", "password", null, null, null, Instant.now(), null);
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("username", "Name", "email@email.com", "Pa$$w0rd!");
            //Act
            var output = userService.createUser(input);
            //Assert
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.name(), userCaptured.getName());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw a data with this value already exists exception")
        void shouldThrowADataWithThisValueAlreadyExistsException(){
            //Arrange
            doReturn(true).when(userRepository).existsByUsername(usernameArgumentCaptor.capture());
            var input = new CreateUserDto("username", "name", "email@email.com", "password");
            //Act & Assert
            assertThrows(DataWithThisValueAlreadyExists.class, () -> userService.createUser(input));

            verify(userRepository, times(1)).existsByUsername(usernameArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());
        }

        @Test
        @DisplayName("Should throw record not created exception")
        void shouldThrowRecordNotCreatedException() {
            //Arrange
            var input = new CreateUserDto(null, "Name", null, null);
            //Act & Assert
            assertThrows(RecordNotCreatedException.class, () -> userService.createUser(input));
            verify(userRepository, times(0)).save(any());
        }
    }

    @Nested
    class findUserByName {
        @Test
        @DisplayName("Should get a user by name with success when optional is present")
        void shouldGetAUserByNameWithSuccessWhenOptionalIsPresent() {
            //Arrange
            var user = new User(UUID.randomUUID(), "username", "name", "email@email.com", "password", null, null, null, Instant.now(), null);
            var userList = List.of(user);
            doReturn(Optional.of(userList)).when(userRepository).findUserByName(nameArgumentCaptor.capture());
            //Act
            var output = userService.findUserByName(user.getName());
            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
            assertEquals(user.getName(), nameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsEmpty() {
            //Arrange
            var userName = "Name";
            doReturn(Optional.empty()).when(userRepository).findUserByName(nameArgumentCaptor.capture());
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> userService.findUserByName(userName));
        }
    }

    @Nested
    class findUserByUsername {
        @Test
        @DisplayName("Should get a user by username with success when optional is present")
        void shouldGetAUserByUsernameWithSuccessWhenOptionalIsPresent() {
            //Arrange
            var user = new User(UUID.randomUUID(), "Name", "username", "email@email.com", "password", null, null, null, Instant.now(), null);
            doReturn(Optional.of(user)).when(userRepository).findUserByUsername(usernameArgumentCaptor.capture());
            //Act
            var output = userService.findUserByUsername(user.getUsername());
            //Assert
            assertNotNull(output);
            assertEquals(user.getUsername(), usernameArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw a record not found exception when optional is empty")
        void shouldThrowARecordNotFoundExceptionWhenOptionalIsEmpty() {
            //Arrange
            var userUsername = "username";
            doReturn(Optional.empty()).when(userRepository).findUserByUsername(usernameArgumentCaptor.capture());
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> userService.findUserByUsername(userUsername));
        }
    }

    @Nested
    class listUsers {
        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {
            //Arrange
            List<Movie> movies = new ArrayList<>();

            List<Review> reviews = new ArrayList<>();

            var user = new User(UUID.randomUUID(), "username", "name", "email@email.com", "password", movies, movies, reviews, Instant.now(), null);
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();
            //Act
            var output = userService.listUsers();
            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class updateUserById {
        @Test
        @DisplayName("Should update a user by id with success when user exists and both the name and password are provided")
        void shouldUpdateAUserByIdWithSuccessWhenUserExistsAndBothTheNameAndPasswordAreProvided() {
            //Arrange
            var updateUserDto = new UpdateUserDto("New Name", "N3w Pa$$w0Rd");

            var user = new User(UUID.randomUUID(), "Name", "username", "email@email.com", "password", null, null, null, Instant.now(), null);

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            //Act
            userService.updateUserById(user.getUserId().toString(), updateUserDto);
            //Assert
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDto.name(), userCaptured.getName());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Should throw a record not found exception and not update when user does not exist")
        void shouldThrowARecordNotFoundExceptionAndNotUpdateWhenUserDoesNotExist(){
            //Arrange
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
            var updateUserDto = new UpdateUserDto("name", "password");
            var userId = UUID.randomUUID();
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> userService.updateUserById(userId.toString(), updateUserDto));
            verify(userRepository, times(0)).save(any());
        }
    }

    @Nested
    class deleteUserById {
        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserNotExists() {
            //Arrange
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            //Act
            userService.deleteUserById(userId.toString());
            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should throw a record not found exception when user was not found")
        void shouldThrowARecordNotFoundExceptionWhenUserWasNotFound() {
            //Arrange
            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            //Act & Assert
            assertThrows(RecordNotFoundException.class, () -> userService.deleteUserById(userId.toString()));
            verify(userRepository, times(0)).deleteById(any());
        }
    }
}
