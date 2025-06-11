package tobyspring.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLogin(
        @NotNull(message = "이메일은 공백일 수 없습니다.")
        @Size(min = 2, message = "이메일은 최소 두 글자 이상입니다.")
        String email,
        @NotNull(message = "패스워드는 공백일 수 없습니다.")
        @Size(min = 8, message = "패스워드는 최소 8글자 이상입니다.")
        String password) {
}
