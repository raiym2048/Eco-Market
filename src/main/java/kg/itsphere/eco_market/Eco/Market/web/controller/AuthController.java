package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.domain.exception.BadCredentialsException;
import kg.itsphere.eco_market.Eco.Market.service.impl.PasswordValidationService;

import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.MyData;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRegisterRequest;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordValidationService passwordValidationService;

    @PostMapping("/register")
    public MyData register(@RequestBody UserRegisterRequest userRegisterRequest) {
//        if(passwordValidationService.validatePassword(userRegisterRequest.getPassword())) {
//            authService.register(userRegisterRequest);
//            return ResponseEntity.ok( "Verification code was sent to "+ userRegisterRequest.getEmail() + "!");
//        }
        authService.register(userRegisterRequest);
        MyData data = new MyData();
        data.setMessage("Verification code was sent to "+ userRegisterRequest.getEmail() + "!");
        return data ;
//        else {
//            throw new BadCredentialsException("Invalid password. Please provide a password with at least 6 characters, containing uppercase, lowercase, and special characters.");
//        }


    }

    @PostMapping("/login")
    public AuthLoginResponse login(@RequestBody AuthLoginRequest authLoginRequest){
        return authService.login(authLoginRequest);
    }
    

}
