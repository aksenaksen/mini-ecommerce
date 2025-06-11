package tobyspring.userservice.dto;

import java.util.Date;

public record ResponseOrder(
        String productId,
        Integer qty,
        Integer unitPrice,
        Integer totalPrice,
        Date createdAt,
        String orderId
) {
}
