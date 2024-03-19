package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.dto.EmailRequest;

public interface EmailService {
    void send_code(String token, EmailRequest request);
}
