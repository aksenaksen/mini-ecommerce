package tobyspring.orderservice.dto;

import lombok.Builder;
import lombok.Data;
import tobyspring.orderservice.domain.Order;
import tobyspring.orderservice.vo.RequestOrder;

import java.util.Date;

@Data
@Builder
public class OrderDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    private Date createdAt;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .productId(order.getProductId())
                .qty(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .unitPrice(order.getUnitPrice())
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static OrderDto of(String userId, RequestOrder request) {
        return OrderDto.builder()
                .productId(request.getProductId())
                .qty(request.getQty())
                .unitPrice(request.getUnitPrice())
                .userId(userId)
                .build();
    }
}
