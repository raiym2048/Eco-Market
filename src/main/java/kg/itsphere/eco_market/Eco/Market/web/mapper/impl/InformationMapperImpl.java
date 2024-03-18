package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.info.Information;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.InformationMapper;
import org.springframework.stereotype.Component;

@Component
public class InformationMapperImpl implements InformationMapper {
    @Override
    public InformationResponse toDto(Information information) {
        InformationResponse informationResponse = new InformationResponse();
        informationResponse.setInfoText(information.getInfoText());
        informationResponse.setPhoneNumber(information.getPhoneNumber());
        informationResponse.setWhatsapp(informationResponse.getWhatsapp());
        informationResponse.setInstagram(information.getInstagram());
        return informationResponse;
    }

    @Override
    public Information toDtoInformation(Information information, InformationRequest informationRequest) {
        information.setInfoText(informationRequest.getInfoText());
        information.setPhoneNumber(informationRequest.getPhoneNumber());
        information.setWhatsapp(informationRequest.getWhatsapp());
        information.setInstagram(informationRequest.getInstagram());
        return information;
    }
}
