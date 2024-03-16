package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse findByEmail(String email);
    void controlUserRole(String userEmail, Map<String, Object> fields);
}
