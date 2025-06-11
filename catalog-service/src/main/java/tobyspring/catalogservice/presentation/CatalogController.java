package tobyspring.catalogservice.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tobyspring.catalogservice.application.CatalogService;
import tobyspring.catalogservice.vo.ResponseCatalog;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return "OK";
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> findAllUsers() {
        List<ResponseCatalog> res = catalogService.findAll();
        return ResponseEntity.ok()
                .body(res);
    }

}
