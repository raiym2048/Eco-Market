package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.info.Information;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationResponse;

public interface InformationMapper {
    InformationResponse toDto(Information information);
    Information toDtoInformation(Information information, InformationRequest informationRequest);
}
