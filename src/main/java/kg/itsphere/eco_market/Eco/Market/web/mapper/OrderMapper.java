package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;

public interface OrderMapper {
    OrderResponse toDto(Order order);
}
