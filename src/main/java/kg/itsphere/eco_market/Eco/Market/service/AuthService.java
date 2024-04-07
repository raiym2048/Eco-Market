package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginResponse;

import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRegisterRequest;


public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);

    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
    User getUserFromToken(String token);

}
