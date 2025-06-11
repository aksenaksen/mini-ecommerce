package tobyspring.userservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tobyspring.userservice.dto.UserDto;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(unique = true,nullable = false)
    private String userId;

    @Column(nullable = false,unique = false)
    private String encryptedPassword;



    public static User of(UserDto userDto , String encryptedPassword) {
        return User.builder()
                .email(userDto.email())
                .name(userDto.name())
                .userId(UUID.randomUUID().toString())
                .encryptedPassword(encryptedPassword)
                .build();
    }
}
