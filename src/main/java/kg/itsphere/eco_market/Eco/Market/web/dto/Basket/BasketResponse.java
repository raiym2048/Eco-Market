package kg.itsphere.eco_market.Eco.Market.web.dto.Basket;

import lombok.Data;

import java.util.List;

@Data
public class BasketResponse {
    private List<String> titles;
    private List<Integer> prices;
    private List<Integer> quantities;
    //    private List<String> imageNames;
    private Integer sum;
    private Integer delivery;
    private Integer total;
}