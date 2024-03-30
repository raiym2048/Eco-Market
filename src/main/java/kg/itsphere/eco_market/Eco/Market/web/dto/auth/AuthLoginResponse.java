package kg.itsphere.eco_market.Eco.Market.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponse {
    private Long id;
    private String email;
    private String username;
    @JsonProperty("access_token")
    private String accessToken;

}
