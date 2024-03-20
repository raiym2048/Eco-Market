package kg.itsphere.eco_market.Eco.Market.repository;

import kg.itsphere.eco_market.Eco.Market.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface TokenRepository extends JpaRepository<Token , Long> {
    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where u.id = :userId and (t.expired = false or t.revoked = false)
""")
    List<Token> findAllValidTokensByUser(Long userId);


    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where u.id = :userId
""")
    List<Token> findAllTokensByUserId(Long userId);

    Optional<Token> findByToken(String token);
}
