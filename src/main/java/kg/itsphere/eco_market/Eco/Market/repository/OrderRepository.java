package kg.itsphere.eco_market.Eco.Market.repository;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
