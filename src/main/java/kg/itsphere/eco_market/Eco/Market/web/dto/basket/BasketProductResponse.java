package kg.itsphere.eco_market.Eco.Market.web.dto.basket;

import lombok.Data;

@Data
public class BasketProductResponse {
    private Long productID;
    private String title;
    private Integer price;
    private Integer quantity;
    private Integer subTotal;
    private String imagePath;
}
