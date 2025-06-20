package tobyspring.userservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tobyspring.userservice.domain.OrderFinder;
import tobyspring.userservice.domain.User;
import tobyspring.userservice.domain.UserRepository;
import tobyspring.userservice.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFinder userFinder;
    private final UserAppender userAppender;
    private final OrderFinder orderFinder;

    public UserDto createUser(UserDto userDto) {
        return userAppender.append(userDto);
    }

    public List<UserDto> findAll() {
        return userFinder.findAllUsers();
    }

    public UserDto findUserById(String userId) {
        UserDto result = userFinder.findByUserId(userId);
        result.setOrders(orderFinder.findByUserId(userId));
        return result;
    }


}
