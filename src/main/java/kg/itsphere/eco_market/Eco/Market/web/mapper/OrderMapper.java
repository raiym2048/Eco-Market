package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface OrderMapper {
    OrderResponse toDto(Order order);

    List<OrderResponse> toDtos(List<Order> orders);

    OrderDetailResponse toDetailDto(Order order);
}
