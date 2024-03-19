package kg.itsphere.eco_market.Eco.Market.controller;

import kg.itsphere.eco_market.Eco.Market.dto.EmailRequest;
import kg.itsphere.eco_market.Eco.Market.dto.UserRegisterRequest;
import kg.itsphere.eco_market.Eco.Market.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private EmailService emailService;
    @PostMapping()
    public String code(@RequestHeader("Authorization") String token, @RequestBody EmailRequest request){
        emailService.send_code(token, request);
        return "We have sent a code to your email!";
    }


}
