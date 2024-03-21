package kg.itsphere.eco_market.Eco.Market.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRegisterRequest;

import java.io.IOException;

public interface AuthService {
    void register(UserRegisterRequest userRegisterRequest);
    AuthLoginResponse login(AuthLoginRequest authLoginRequest);
    public User getUserFromToken(String token);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
