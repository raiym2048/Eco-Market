package kg.itsphere.eco_market.Eco.Market.web.controller;

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
//    @PostMapping("/send")
//    public String code(@RequestHeader("Authorization") String token, @RequestBody EmailRequest request){
//        emailService.send_code(token, request);
//        return "We have sent a code to your email!";
//    }
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestBody CodeRequest request){
        emailService.verify(email, request);
        return ResponseEntity.ok("Your email is linked successfully!");
    }

    @PostMapping("/recovery")
    public String recovery(@RequestBody EmailRequest request){
        emailService.recovery(request.getEmail());
        return "We sent a link for recovery your password to your email!";
    }

    @PostMapping("/recovery-password")
    public String recoveryPassword(@RequestParam String code, @RequestBody RecoveryRequest request){
        emailService.recoveryPassword(code, request);
        return "Password updated successfully!";
    }


}
