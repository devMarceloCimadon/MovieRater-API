package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.User.CreateUserDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.UpdateUserDto;
import proj.devMarceloCimadon.MovieRater.Exceptions.DataWithThisValueAlreadyExists;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotCreatedException;
import proj.devMarceloCimadon.MovieRater.Exceptions.RecordNotFoundException;
import proj.devMarceloCimadon.MovieRater.Models.User;
import proj.devMarceloCimadon.MovieRater.Repositories.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.username())){
            throw new DataWithThisValueAlreadyExists("username", createUserDto.username());
        }
        if (createUserDto.username() == null || createUserDto.email() == null || createUserDto.password() == null){
            throw new RecordNotCreatedException();
        }
        var user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername(createUserDto.username());
        user.setName(createUserDto.name());
        user.setEmail(createUserDto.email());
        user.setPassword(createUserDto.password());
        user.setCreationTimestamp(Instant.now());
        user.setUpdateTimestamp(null);

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    }

    public Optional<List<User>> findUserByName(String name) {
        var users = userRepository.findUserByName(name);
        if (users.isEmpty()) {
            throw new RecordNotFoundException("Name", name);
        }
        return users;
    }

    public Optional<User> findUserByUsername(String username) {
        var user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("Username", username);
        }
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new RecordNotFoundException("ID", userId);
        }
        var user = userEntity.get();
        if (updateUserDto.name() != null) {
            user.setName(updateUserDto.name());
        }
        if (updateUserDto.password() != null) {
            user.setPassword(updateUserDto.password());
        }

        userRepository.save(user);
    }

    public void deleteUserById(String userId) {
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);
        if (!userExists) {
            throw new RecordNotFoundException("ID", userId);
        }
        userRepository.deleteById(id);
    }
}
