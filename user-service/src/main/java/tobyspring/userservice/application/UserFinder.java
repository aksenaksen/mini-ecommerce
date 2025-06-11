package tobyspring.userservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tobyspring.userservice.domain.User;
import tobyspring.userservice.domain.UserRepository;
import tobyspring.userservice.dto.UserDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFinder {
    private final UserRepository userRepository;

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDto::from)
                .orElseThrow();
    }

    public UserDto findByUserId(String userId) {
        return UserDto.from(userRepository.findByUserId(userId)
                .orElseThrow());
    }
}
