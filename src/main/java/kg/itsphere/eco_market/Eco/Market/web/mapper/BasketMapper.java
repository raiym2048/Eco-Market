package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;

public interface BasketMapper {
    BasketResponse toDto(Basket basket);
}
