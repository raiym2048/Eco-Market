package kg.itsphere.eco_market.Eco.Market.web.dto.basket;

import lombok.Data;

@Data
public class BasketRequest {
    private Long productId;
    private Integer quantity;
}
