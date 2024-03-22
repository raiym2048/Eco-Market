package kg.itsphere.eco_market.Eco.Market.web.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime dateTime;
    private Integer totalSum;
}
