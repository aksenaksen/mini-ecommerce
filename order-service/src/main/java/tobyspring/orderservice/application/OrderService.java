package tobyspring.orderservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tobyspring.orderservice.domain.Order;
import tobyspring.orderservice.domain.OrderRepository;
import tobyspring.orderservice.dto.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = Order.createOrder(orderDto);
        orderRepository.save(order);
        return OrderDto.from(order);
    }

    public List<OrderDto> getOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId)
                .map(OrderDto::from)
                .orElseThrow();
    }

}
