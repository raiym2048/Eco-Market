package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.CodeRequest;

import kg.itsphere.eco_market.Eco.Market.web.dto.user.RecoveryRequest;
import kg.itsphere.eco_market.Eco.Market.repository.UserRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Override
    public void verify( CodeRequest request) {
        Optional<User> user = userRepository.findByVerifyCode(request.getCode());
        if(user.isEmpty()){
            throw new NotFoundException("Code is wrong", HttpStatus.NOT_FOUND);
        }
        user.get().setVerified(true);
        user.get().setVerifyCode(null);
        userRepository.save(user.get());


//        if(Objects.equals(user.get().getVerifyCode(), request.getCode())){
//            user.get().setVerified(true);
//            userRepository.save(user.get());
//        } else
//            throw new BadRequestException("Code is wrong!");

    }

    @Override
    public void recovery(String email) {
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new NotFoundException("Account with this email doesn't exist!", HttpStatus.NOT_FOUND);
        user.get().setUuid(code);
        userRepository.save(user.get());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ecomarket1111@gmail.com");
        message.setTo(email);
        message.setText("This is link for recovery your password: http://localhost:5050/api/v1/email/recovery-password?code=" + code + "\n\nDon't share with id !");
        message.setSubject("Eco-Market. Account verifying");
        mailSender.send(message);
    }

    @Override
    public void recoveryPassword(String code, RecoveryRequest request) {
        Optional<User> user = userRepository.findByUuid(code);
        String pass1 = request.getNewPassword1();
        String pass2 = request.getNewPassword2();
        if(!Objects.equals(pass1, pass2))
            throw new BadRequestException("Passwords don't match!");
        user.get().setPassword(encoder.encode(pass1));
        userRepository.save(user.get());
    }

}
