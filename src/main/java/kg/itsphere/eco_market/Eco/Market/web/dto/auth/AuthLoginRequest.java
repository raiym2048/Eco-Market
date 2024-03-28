package kg.itsphere.eco_market.Eco.Market.web.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {
    private String username;
    private String password;
}
