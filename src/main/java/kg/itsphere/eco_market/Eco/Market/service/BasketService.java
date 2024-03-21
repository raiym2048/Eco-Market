package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;

public interface BasketService {
    void add(BasketRequest request, String token);

    BasketResponse show(String token);

    void delete(BasketRequest request, String token);

    void update(BasketRequest request, String token);
}
