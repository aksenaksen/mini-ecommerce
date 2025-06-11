package tobyspring.userservice.dto;

import tobyspring.userservice.domain.User;

import java.util.Date;

public record UserDto(
        String email,
        String password,
        String name,
        String userId,
        Date createdAt
) {

    public static UserDto of(String email, String password, String name) {
        return new UserDto(email, password, name, null, new Date());
    }

    public static UserDto from(User user) {
        return new UserDto(user.getEmail(), null, user.getName(), user.getUserId(), null);
    }

}
