package tobyspring.orderservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Optional<Order> findByOrderId(String orderId);
    public List<Order> findAllByUserId(String userId);
}
