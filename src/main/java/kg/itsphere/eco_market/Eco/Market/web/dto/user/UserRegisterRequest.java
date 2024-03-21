package kg.itsphere.eco_market.Eco.Market.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
}
