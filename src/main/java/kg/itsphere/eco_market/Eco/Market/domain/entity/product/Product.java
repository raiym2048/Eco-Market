package kg.itsphere.eco_market.Eco.Market.domain.entity.product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Integer price;
//    @OneToOne
//    private Image image;
    private Integer quantity;
}
