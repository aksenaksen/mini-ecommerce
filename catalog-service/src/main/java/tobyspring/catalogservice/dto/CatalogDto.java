package tobyspring.catalogservice.dto;


import lombok.*;
import tobyspring.catalogservice.domain.Catalog;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDto implements Serializable {
    private String productId;
    private String productName;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;


    public static CatalogDto from(Catalog catalog) {
        return CatalogDto.builder()
                .productId(catalog.getProductId())
                .productName(catalog.getProductName())
                .qty(catalog.getStock())
                .unitPrice(catalog.getUnitPrice())
                .totalPrice(catalog.getUnitPrice()* catalog.getStock())
                .build();
    }
}
