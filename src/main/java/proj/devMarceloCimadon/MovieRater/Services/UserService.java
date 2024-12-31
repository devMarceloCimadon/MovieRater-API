package proj.devMarceloCimadon.MovieRater.Services;

import org.springframework.stereotype.Service;
import proj.devMarceloCimadon.MovieRater.Dto.User.CreateUserDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.ResponseUserDto;
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

    public List<User> findUserByName(String name) {
        return userRepository.findUserByName(name).orElseThrow(() -> new RecordNotFoundException("Name", name));
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RecordNotFoundException("Username", username));
    }

    public List<ResponseUserDto> listUsers() {
        var users = userRepository.findAll();
        return users.stream().map(ResponseUserDto :: fromEntity).toList();
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
