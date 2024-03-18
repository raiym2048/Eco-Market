package kg.itsphere.eco_market.Eco.Market.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.itsphere.eco_market.Eco.Market.config.JwtService;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.dto.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.dto.UserRegisterRequest;
import kg.itsphere.eco_market.Eco.Market.entities.Token;
import kg.itsphere.eco_market.Eco.Market.enums.TokenType;
import kg.itsphere.eco_market.Eco.Market.repository.TokenRepository;
import kg.itsphere.eco_market.Eco.Market.repository.UserRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;

    @Override
    public void register(UserRegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadCredentialsException("User with email"+ request.getEmail() + "already exist ");
        }
        else if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new BadCredentialsException("User with username"+ request .getUsername() + "already exist ");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        var saveUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saveUser, jwtToken);

    }

    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authLoginRequest.getEmail(),
                            authLoginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        var user = userRepository.findByEmail(authLoginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("user not found.."));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthLoginResponse.builder()
                .id(user.getId())
                .email(authLoginRequest.getEmail())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }
    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
    private AuthLoginResponse convertToResponse(User user) {
        AuthLoginResponse response = new AuthLoginResponse();
        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        response.setAccessToken(token);
        response.setRefreshToken(refreshToken);

        return response;
    }
//    @Override
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final String authHeader = request.getHeader("Authorization");
//        final String refreshToken;
//        final String username;
//        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        username = jwtService.extractUsername(refreshToken);
//        if(username != null){
//            var user = this.userRepository.findByUsername(username).orElseThrow();
//            if(jwtService.isTokenValid(refreshToken, user)){
//                var accessToken = jwtService.generateToken(user);
//                AuthLoginResponse authResponse = new AuthLoginResponse();
//                authResponse.setAccessToken(accessToken);
//                authResponse.setRefreshToken(refreshToken);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }

}
