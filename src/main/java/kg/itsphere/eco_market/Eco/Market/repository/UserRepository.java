package kg.itsphere.eco_market.Eco.Market.repository;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(String uuid);
    Optional<User> findByVerifyCode(String code);

}
