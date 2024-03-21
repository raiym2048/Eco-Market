package kg.itsphere.eco_market.Eco.Market.domain.entity;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
