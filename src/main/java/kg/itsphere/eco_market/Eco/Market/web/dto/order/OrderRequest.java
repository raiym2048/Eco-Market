package kg.itsphere.eco_market.Eco.Market.web.dto.order;

import lombok.Data;

@Data
public class OrderRequest {
    private String number;
    private String address;
    private String orientation;
    private String comment;
}
