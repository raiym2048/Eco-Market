package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.dto.EmailRequest;
import kg.itsphere.eco_market.Eco.Market.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void send_code(String token, EmailRequest request) {
        
    }
}
