package kg.itsphere.eco_market.Eco.Market.repository;

import jakarta.transaction.Transactional;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findAllByCategory(Category category);
    @Transactional
    void deleteByName(String name);

    List<Product> findAllByName(String name);

    List<Product> findAllByCategoryAndName(Category category, String name);
}
