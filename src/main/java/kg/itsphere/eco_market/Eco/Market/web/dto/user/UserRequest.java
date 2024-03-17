package kg.itsphere.eco_market.Eco.Market.web.dto.user;

import jakarta.persistence.Column;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String role;
}
