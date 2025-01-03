package proj.devMarceloCimadon.MovieRater.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proj.devMarceloCimadon.MovieRater.Dto.User.CreateUserDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.ResponseUserDto;
import proj.devMarceloCimadon.MovieRater.Dto.User.UpdateUserDto;
import proj.devMarceloCimadon.MovieRater.Models.User;
import proj.devMarceloCimadon.MovieRater.Services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/user/" + userId.toString())).build();
    }

    @GetMapping("/name/{userName}")
    public ResponseEntity<List<ResponseUserDto>> getUserByName(@PathVariable("userName") String userName) {
        var users = userService.findUserByName(userName);
        var usersResponse = users.stream().map(ResponseUserDto :: fromEntity).toList();
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/username/{userUsername}")
    public ResponseEntity<ResponseUserDto> getUserByUsername(@PathVariable("userUsername") String userUsername) {
        var user = userService.findUserByUsername(userUsername);
        var usersResponse = ResponseUserDto.fromEntity(user);
        return ResponseEntity.ok(usersResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUserDto>> listUsers() {
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
