package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketProductResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;

public interface BasketMapper {
    BasketProductResponse toDto(Product product, int quantity);

    BasketResponse toDtoS(Basket basket);
}
