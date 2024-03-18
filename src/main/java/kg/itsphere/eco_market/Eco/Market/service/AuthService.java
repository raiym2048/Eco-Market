package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.dto.UserRegisterRequest;

public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);
    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
}
