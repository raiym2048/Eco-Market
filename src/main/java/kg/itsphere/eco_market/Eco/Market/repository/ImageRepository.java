package kg.itsphere.eco_market.Eco.Market.repository;

import jakarta.transaction.Transactional;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);

    @Transactional
    void deleteByName(String name);
}
