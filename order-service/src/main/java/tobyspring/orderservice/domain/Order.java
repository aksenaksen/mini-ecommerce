package tobyspring.orderservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import tobyspring.orderservice.dto.OrderDto;

import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String productId;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, updatable = false,insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;


    public static Order createOrder(OrderDto orderDto) {
        return Order.builder()
                .productId(orderDto.getProductId())
                .orderId(UUID.randomUUID().toString())
                .userId(orderDto.getUserId())
                .unitPrice(orderDto.getUnitPrice())
                .totalPrice(orderDto.getQty() * orderDto.getUnitPrice())
                .qty(orderDto.getQty())
                .build();
    }

}
