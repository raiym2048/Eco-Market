package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.web.dto.Basket.BasketResponse;

public interface BasketMapper {
    BasketResponse toDto(Basket basket);
}
