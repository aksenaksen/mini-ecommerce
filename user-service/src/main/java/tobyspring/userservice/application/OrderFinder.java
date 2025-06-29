package tobyspring.userservice.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tobyspring.userservice.dto.ResponseOrder;

import java.util.List;

@Component
public class OrderFinder {

    private final RestClient restClient;

    public OrderFinder(@Value("${order-service.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<ResponseOrder> findByUserId(String userId) {
        return restClient.get()
                .uri("/{userId}/orders", userId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ResponseOrder>>() {});
    }
}
