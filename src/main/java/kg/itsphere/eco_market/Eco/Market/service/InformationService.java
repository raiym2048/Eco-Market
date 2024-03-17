package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationResponse;

public interface InformationService {
    void createInfo(InformationRequest informationRequest);
    InformationResponse findById(Long id);
    void updateById(Long id, InformationRequest informationRequest);
    void deleteById(Long id);
}
