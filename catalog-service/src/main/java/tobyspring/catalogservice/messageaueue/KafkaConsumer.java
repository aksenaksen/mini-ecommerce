package tobyspring.catalogservice.messageaueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tobyspring.catalogservice.domain.Catalog;
import tobyspring.catalogservice.domain.CatalogRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka message received: {}", kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        }
        catch (JsonProcessingException ex){
            ex.printStackTrace();
        }

        Catalog catalog = catalogRepository.findByProductId((String) map.get("productId"))
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        if (catalog != null){
            catalog.changeStock((Integer) map.get("qty"));
        }

        catalogRepository.save(catalog);
    }

}
