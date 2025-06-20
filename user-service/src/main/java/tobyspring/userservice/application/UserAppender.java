package tobyspring.userservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tobyspring.userservice.domain.User;
import tobyspring.userservice.domain.UserRepository;
import tobyspring.userservice.dto.UserDto;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto append(UserDto userDto) {
        String password = bCryptPasswordEncoder.encode(userDto.getPassword());
        User user = userRepository.save(User.of(userDto,password));
        return UserDto.from(user);
    }



}
