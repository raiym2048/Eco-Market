package kg.itsphere.eco_market.Eco.Market.web.dto.user;

import lombok.Data;

@Data
public class RecoveryRequest {
    private String newPassword1;
    private String newPassword2;
}