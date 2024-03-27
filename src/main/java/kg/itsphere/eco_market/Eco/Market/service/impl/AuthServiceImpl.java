package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import kg.itsphere.eco_market.Eco.Market.config.JwtService;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.Role;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.AuthLoginResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRegisterRequest;
import kg.itsphere.eco_market.Eco.Market.domain.entity.Token;
import kg.itsphere.eco_market.Eco.Market.domain.entity.enums.TokenType;
import kg.itsphere.eco_market.Eco.Market.repository.BasketRepository;
import kg.itsphere.eco_market.Eco.Market.repository.TokenRepository;
import kg.itsphere.eco_market.Eco.Market.repository.UserRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;
    private final BasketRepository basketRepository;
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void register(UserRegisterRequest request) {
            if (userRepository.findByUsername(request.getUsername()).isPresent() || userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new NotFoundException("User " + request.getUsername() + " already exist ", HttpStatus.NOT_FOUND);
            }
            if(request.getUsername().isEmpty() || request.getEmail().isEmpty()) {
                throw new BadRequestException("Your email or username can't be empty");
            }
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new NotFoundException("User with username " + request.getUsername() + " already exist ", HttpStatus.NOT_FOUND);
            } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new NotFoundException("User with email " + request.getEmail() + " already exist ", HttpStatus.NOT_FOUND);
            } else if (!request.getEmail().contains("@")) {
                throw new BadRequestException("Invalid email!");
            } else if (request.getPhoneNumber().length() != 13 || !request.getPhoneNumber().startsWith("+996")) {
                throw new BadRequestException("Invalid number");
            }
            var user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setRole(Role.ROLE_USER);
            user.setPhoneNumber(request.getPhoneNumber());
            var saveUser = userRepository.saveAndFlush(user);

            Basket basket = new Basket();
            user.setVerified(false);
            basket.setUser(saveUser);
            var jwtToken = jwtService.generateToken(user);

            saveUser.setBasket(basketRepository.saveAndFlush(basket));
            saveUserToken(saveUser, jwtToken);
            send_code(request.getEmail());

    }

        private void revokeAllUserTokens (User user){
            var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
            if (validUserTokens.isEmpty()) {
                return;
            }
            validUserTokens.forEach(t -> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }


        private void saveUserToken (User user, String jwtToken){
            var token = Token.builder()
                    .user(user)
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .build();
            tokenRepository.save(token);
        }

    @Override
    public AuthLoginResponse login(AuthLoginRequest authLoginRequest) {
        Optional<User> user1 = userRepository.findByEmail(authLoginRequest.getEmail());
        if (user1.get().getVerified()) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authLoginRequest.getUsername(),
                                authLoginRequest.getPassword()
                        )
                );
            } catch (AuthenticationException e) {
                throw new BadRequestException("Invalid username or password");
            }
            var user = userRepository.findByUsername(authLoginRequest.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("user not found.."));
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthLoginResponse.builder()
                    .id(user.getId())
                    .username(authLoginRequest.getUsername())
                    .accessToken(jwtToken)

                    .build();
        } else {
            throw new BadRequestException("You should verify your email");
        }
    }
    private void send_code(String to){
        try {
            String code = "";
            Random random = new Random();
            for (int k = 0; k < 6; k++) {
                if (random.nextInt(2) == 0)
                    code += (char) (random.nextInt(26) + 65);
                else
                    code += (char) (random.nextInt(10) + 48);
            }

            Optional<User> user = userRepository.findByEmail(to);

            user.get().setVerifyCode(code);
            userRepository.save(user.get());
            String email = to;
            if (email == null)
                throw new BadRequestException("Please, write your email!");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ecomarket1111@gmail.com");
            message.setTo(to);
            message.setText("This is code for verifying your account: " + code + "\n\nDon't share with this code ");
            message.setSubject("Eco-Market . Account verifying");
            mailSender.send(message);
        } catch (MailException e) {
            throw new BadRequestException("Email is invalid , please enter correct email.");

        }
    }

    @Override
    public User getUserFromToken(String token) {

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        if (chunks.length != 3)
            throw new BadCredentialsException("Wrong token!");
        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            byte[] decodedBytes = decoder.decode(chunks[1]);
            object = (JSONObject) jsonParser.parse(decodedBytes);
        } catch (ParseException e) {
            throw new BadCredentialsException("Wrong token!!");
        }
        return userRepository.findByUsername(String.valueOf(object.get("sub"))).orElseThrow(() -> new BadCredentialsException("Wrong token!!!"));
    }
}

