package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.user.CodeRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.RecoveryRequest;

public interface EmailService {

    void verify(String token, CodeRequest request);

    void recovery(String email);

    void recoveryPassword(String code, RecoveryRequest request);
}
