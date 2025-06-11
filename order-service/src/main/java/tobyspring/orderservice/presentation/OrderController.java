package tobyspring.orderservice.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobyspring.orderservice.application.OrderService;
import tobyspring.orderservice.dto.OrderDto;
import tobyspring.orderservice.vo.RequestOrder;
import tobyspring.orderservice.vo.ResponseOrder;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/health_check")
    public String status() {
        return "OK";
    }
//    1738e14b-86e2-4aff-9fdf-76e6d6b6005a

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,@RequestBody RequestOrder request){
        OrderDto orderDto = OrderDto.of(userId,request);
        OrderDto response = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseOrder.from(response));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId){
        List<OrderDto> orderDtoList = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoList.stream()
                        .map(ResponseOrder::from)
                        .toList());
    }
}
