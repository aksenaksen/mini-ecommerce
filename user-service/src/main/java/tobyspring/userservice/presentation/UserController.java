package tobyspring.userservice.presentation;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobyspring.userservice.application.UserService;
import tobyspring.userservice.dto.RequestCreateUser;
import tobyspring.userservice.dto.ResponseOrder;
import tobyspring.userservice.dto.ResponseUser;
import tobyspring.userservice.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Tag(name = "user-controllers", description = "일반 사용자 컨트롤러")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Environment env;

    @Operation(summary = "Health check API",description = "Health check 를 위한 api")
    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
    public String status() {
        return String.format("It's working in User Service"+
                ", port(local.server.port)="+ env.getProperty("local.server.port")+
                "port(server.port)="+ env.getProperty("server.port")+
                "token secret="+ env.getProperty("token.secret")+
                "token expiry="+ env.getProperty("token.expired"));
    }

    @Timed(value = "users.status", longTask = true)
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestCreateUser request) {
        UserDto res = userService.createUser(UserDto.of(request.email(), request.password(), request.name()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUser.from(res));
    }

    @Operation(summary = "사용자 목록 조회",description = "현재 회원가입 된 사용자 목록을 조회하기위한 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "OK"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERRORS")
    })
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
