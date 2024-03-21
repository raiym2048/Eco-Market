package kg.itsphere.eco_market.Eco.Market.domain.entity.product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "path")
    private String path;
}
