package tobyspring.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import tobyspring.orderservice.dto.OrderDto;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private Date createdAt;
    private String orderId;


    public static ResponseOrder from(OrderDto dto) {
        return ResponseOrder.builder()
                .productId(dto.getProductId())
                .qty(dto.getQty())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(dto.getTotalPrice())
                .orderId(dto.getOrderId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
