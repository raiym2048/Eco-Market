package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.dto.CodeRequest;
import kg.itsphere.eco_market.Eco.Market.dto.EmailRequest;
import kg.itsphere.eco_market.Eco.Market.dto.RecoveryRequest;

public interface EmailService {
    void send_code(String token, EmailRequest request);
    void verify(String token, CodeRequest request);

    void recovery(String email);

    void recoveryPassword(String code, RecoveryRequest request);
}
