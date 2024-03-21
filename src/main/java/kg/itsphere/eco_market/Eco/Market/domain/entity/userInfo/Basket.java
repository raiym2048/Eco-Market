package kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany
    private List<BasketItem> items;

}
