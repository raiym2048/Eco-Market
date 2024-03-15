package kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Product> productList;
    private Long totalPrice;
}
