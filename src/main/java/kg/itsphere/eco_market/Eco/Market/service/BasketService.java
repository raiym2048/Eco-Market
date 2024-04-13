package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketProductResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;

public interface BasketService {
    void add(Long id, String token);

    BasketResponse show(String token);

    void delete(BasketItem item, Basket basket);
    void addOne(String token, Long id);
    void decreaseOne(String token, Long id);

    void check(String token);

    OrderResponse buy(OrderRequest request, String token);

    void clear(String token);

    BasketProductResponse showDetail(Long id, String token);
}
