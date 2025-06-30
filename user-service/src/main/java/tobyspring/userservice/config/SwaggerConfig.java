package tobyspring.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "USER SERVICE",
        description = "User Service Api")
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/users/**", "/health-check"};

        return GroupedOpenApi.builder()
                .group("일반 사용자 관리 User도메인 api")
                .pathsToMatch(paths)
                .build();
    }

}
