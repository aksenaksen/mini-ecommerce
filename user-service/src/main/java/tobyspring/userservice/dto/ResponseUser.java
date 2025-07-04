package tobyspring.userservice.dto;

import java.util.List;

public record ResponseUser(
        String email,
        String name,
        String userId,
        List<ResponseOrder> orders
)
{

    public static ResponseUser from(UserDto user) {
        return new ResponseUser(user.getEmail(), user.getName(), user.getUserId(),user.getOrders());

    }
}
