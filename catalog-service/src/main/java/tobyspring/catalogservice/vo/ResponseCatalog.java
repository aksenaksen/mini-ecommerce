package tobyspring.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tobyspring.catalogservice.domain.Catalog;
import tobyspring.catalogservice.dto.CatalogDto;

import java.util.Date;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private Date createdAt;


    public static ResponseCatalog from(CatalogDto dto) {
        return new ResponseCatalog(dto.getProductId(), dto.getProductName(),dto.getUnitPrice(),dto.getQty(),null);
    }
    public static ResponseCatalog from(Catalog catalog) {
        return new ResponseCatalog(catalog.getProductId(),
                catalog.getProductName(),
                catalog.getUnitPrice(),
                catalog.getStock(),
                catalog.getCreatedAt());

    }
}
