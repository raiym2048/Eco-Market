package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.Basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.Basket.BasketResponse;

public interface BasketService {
    void add(BasketRequest request, String token);

    BasketResponse show(String token);

    void delete(BasketRequest request, String token);

    void update(BasketRequest request, String token);
}
