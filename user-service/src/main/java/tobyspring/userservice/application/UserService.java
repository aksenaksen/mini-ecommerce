package tobyspring.userservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tobyspring.userservice.domain.User;
import tobyspring.userservice.domain.UserRepository;
import tobyspring.userservice.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFinder userFinder;
    private final UserAppender userAppender;

    public UserDto createUser(UserDto userDto) {
        return userAppender.append(userDto);
    }

    public List<UserDto> findAll() {
        return userFinder.findAllUsers();
    }

    public UserDto findUserById(String userId) {
        return userFinder.findByUserId(userId);
    }
}
