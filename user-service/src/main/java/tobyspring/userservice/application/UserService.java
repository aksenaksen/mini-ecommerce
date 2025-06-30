package tobyspring.userservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import tobyspring.userservice.domain.OrderClient;
import tobyspring.userservice.dto.ResponseOrder;
import tobyspring.userservice.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFinder userFinder;
    private final UserAppender userAppender;
//    private final OrderFinder orderFinder;
    private final OrderClient orderClient;

    private final CircuitBreakerFactory circuitBreakerFactory;


    public UserDto createUser(UserDto userDto) {
        return userAppender.append(userDto);
    }

    public List<UserDto> findAll() {
        return userFinder.findAllUsers();
    }

    public UserDto findUserById(String userId) {
        UserDto result = userFinder.findByUserId(userId);

        log.info("Before call orders microService");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        List<ResponseOrder> orderList = circuitBreaker.run(() -> orderClient.getOrders(userId),
                throwable -> new ArrayList<>());
        log.info("After call orders microService");


        result.setOrders(orderList);
//        result.setOrders(orderFinder.findByUserId(userId));
        return result;
    }


}
