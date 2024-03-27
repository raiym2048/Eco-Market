package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.web.dto.auth.MyData;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.CodeRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.EmailRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.RecoveryRequest;
import kg.itsphere.eco_market.Eco.Market.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private EmailService emailService;

    @PostMapping("/verify")
    public MyData verify(@RequestParam String email, @RequestBody CodeRequest request){
        emailService.verify(email, request);
        MyData data = new MyData();
        data.setMessage("Your email is linked successfully" );
        return data;
    }

    @PostMapping("/recovery")
    public MyData recovery(@RequestBody EmailRequest request){
        emailService.recovery(request.getEmail());
        MyData data = new MyData();
        data.setMessage("We sent a link for recovery your password to your email!");
        return data;
    }

    @PostMapping("/recovery-password")
    public String recoveryPassword(@RequestParam String code, @RequestBody RecoveryRequest request){
        emailService.recoveryPassword(code, request);
        return "Password updated successfully!";
    }


}
