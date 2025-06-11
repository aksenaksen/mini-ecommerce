package tobyspring.orderservice.vo;

import lombok.Data;
import tobyspring.orderservice.dto.OrderDto;

@Data
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;


}
