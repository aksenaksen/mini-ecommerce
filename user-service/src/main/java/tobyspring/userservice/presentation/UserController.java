package tobyspring.userservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobyspring.userservice.application.UserService;
import tobyspring.userservice.dto.RequestCreateUser;
import tobyspring.userservice.dto.ResponseUser;
import tobyspring.userservice.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in User Service"+
                ", port(local.server.port)="+ env.getProperty("local.server.port")+
                "port(server.port)="+ env.getProperty("server.port")+
                "token secret="+ env.getProperty("token.secret")+
                "token expiry="+ env.getProperty("token.expired"));
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestCreateUser request) {
        UserDto res = userService.createUser(UserDto.of(request.email(), request.password(), request.name()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUser.from(res));
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> findAllUsers() {
        List<ResponseUser> res = userService.findAll().stream()
                .map(ResponseUser::from)
                .toList();
        return ResponseEntity.ok()
                .body(res);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> findUser(@PathVariable String userId) {
        ResponseUser user = ResponseUser.from(userService.findUserById(userId));
        return ResponseEntity.ok()
                .body(user);
    }


}
