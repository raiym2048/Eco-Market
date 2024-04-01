package kg.itsphere.eco_market.Eco.Market.domain.entity.product;

import jakarta.persistence.*;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.OrderItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "image")
    private List<Product> product;
}
