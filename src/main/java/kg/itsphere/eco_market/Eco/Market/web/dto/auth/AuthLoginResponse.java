package kg.itsphere.eco_market.Eco.Market.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class AuthLoginResponse {
    private String email;
    private String password;
    @JsonProperty("access_token")
    private String accessToken;
}
