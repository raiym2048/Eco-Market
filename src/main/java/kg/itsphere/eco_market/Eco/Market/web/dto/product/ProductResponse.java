package kg.itsphere.eco_market.Eco.Market.web.dto.product;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private int price;
    private int quantity;
}
