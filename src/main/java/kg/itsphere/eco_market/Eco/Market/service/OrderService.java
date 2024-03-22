package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponseForAdmin;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> all(String token);

    OrderDetailResponse detail(String token, Long id);

    List<OrderResponse> getAllOrders();
    OrderDetailResponseForAdmin getPersonOrder(Long id);
}
