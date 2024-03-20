package kg.itsphere.eco_market.Eco.Market.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.dto.UserRegisterRequest;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest userRegisterRequest){
        authService.register(userRegisterRequest);
        return "User: " + userRegisterRequest.getUsername() +  " - added successfully!";
    }
    @PostMapping("/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest authLoginRequest){
        return authService.login(authLoginRequest);
    }

   @PostMapping("/refresh")
   public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
