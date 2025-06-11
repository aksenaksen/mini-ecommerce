package tobyspring.catalogservice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tobyspring.catalogservice.domain.CatalogRepository;
import tobyspring.catalogservice.dto.CatalogDto;
import tobyspring.catalogservice.vo.ResponseCatalog;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<ResponseCatalog> findAll(){
        return catalogRepository.findAll()
                .stream()
                .map(ResponseCatalog::from)
                .toList();
    }
}
