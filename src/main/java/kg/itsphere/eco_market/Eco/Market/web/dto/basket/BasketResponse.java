package kg.itsphere.eco_market.Eco.Market.web.dto.basket;

import lombok.Data;

import java.util.List;

@Data
public class BasketResponse {
    private List<BasketProductResponse> productResponses;
    private Integer sum;
    private Integer delivery;
    private Integer total;
}