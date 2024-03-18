package kg.itsphere.eco_market.Eco.Market.repository;

import jakarta.transaction.Transactional;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    @Transactional
    void deleteByName(String name);
}
