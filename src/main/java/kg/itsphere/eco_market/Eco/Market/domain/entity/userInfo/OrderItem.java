package kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Long imageId;
    private Integer quantity;
    @ManyToOne
    private Order order;
}
