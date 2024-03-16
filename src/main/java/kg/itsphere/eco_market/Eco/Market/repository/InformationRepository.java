package kg.itsphere.eco_market.Eco.Market.repository;

import kg.itsphere.eco_market.Eco.Market.domain.entity.info.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
}
