package kg.itsphere.eco_market.Eco.Market.web.dto.order;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailResponseForAdmin {
    private Long id;
    private LocalDateTime dateTime;
    private String userEmail;
    private Integer sum;
    private Integer delivery;
    private List<String> names;
    private List<Long> imageIds;
    private List<Integer> prices;
    private List<Integer> quantities;
    private List<Integer> detailSums;
}
