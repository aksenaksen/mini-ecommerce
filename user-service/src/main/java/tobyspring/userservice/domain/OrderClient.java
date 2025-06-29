package tobyspring.userservice.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tobyspring.userservice.dto.ResponseOrder;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderClient {

    @GetMapping("/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
