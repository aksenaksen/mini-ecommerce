package tobyspring.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import tobyspring.userservice.domain.User;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class UserDto {

    private String email;
    private String password;
    private String name;
    private String userId;
    private Date createdAt;
    private List<ResponseOrder> orders;

    private UserDto(String email, String password, String name, String userId, Date createdAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userId = userId;
        this.createdAt = createdAt != null ? new Date(createdAt.getTime()) : null; // 방어적 복사
    }

    public static UserDto of(String email, String password, String name) {
        return new UserDto(email, password, name, null, new Date());
    }

    public static UserDto from(User user) {
        return new UserDto(user.getEmail(), null, user.getName(), user.getUserId(), null);
    }


}
