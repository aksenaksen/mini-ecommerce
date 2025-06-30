package tobyspring.orderservice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobyspring.orderservice.application.OrderService;
import tobyspring.orderservice.dto.OrderDto;
import tobyspring.orderservice.messgaequeue.KafkaProducer;
import tobyspring.orderservice.messgaequeue.OrderProducer;
import tobyspring.orderservice.vo.RequestOrder;
import tobyspring.orderservice.vo.ResponseOrder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;


    @GetMapping("/health_check")
    public String status() {
        return "OK";
    }
//    1738e14b-86e2-4aff-9fdf-76e6d6b6005a

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,@RequestBody RequestOrder request){
        OrderDto orderDto = OrderDto.of(userId,request);
        OrderDto result = orderService.createOrder(orderDto);

        ObjectMapper mapper = new ObjectMapper();
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setUserId(userId);
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ResponseOrder response = ResponseOrder.from(orderDto);


        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders", orderDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createOrder(@PathVariable("userId") String userId){


        log.info("Before add order microService");
        List<OrderDto> orderDtoList = orderService.getOrdersByUserId(userId);
        log.info("After add order microService");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoList.stream()
                        .map(ResponseOrder::from)
                        .toList());
    }
}
