package kg.itsphere.eco_market.Eco.Market.web.dto.Basket;

import lombok.Data;

@Data
public class BasketRequest {
    private Long productId;
    private Integer quantity;
}
