package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.OrderItem;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.OrderMapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderMapperImpl implements OrderMapper {
    @Override
    public OrderResponse toDto(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setDateTime(order.getDateTime());
        int sum = 0;
        for(OrderItem item: order.getItems()){
            sum += item.getQuantity() * item.getPrice();
        }
        response.setTotalSum(sum);
        return  response;
    }

    @Override
    public List<OrderResponse> toDtos(List<Order> orders) {
        List<OrderResponse> responses = new ArrayList<>();
        for(Order order: orders)
            responses.add(toDto(order));
        return responses;
    }

    @Override
    public OrderDetailResponse toDetailDto(Order order) {
        OrderDetailResponse response = new OrderDetailResponse();
        response.setId(order.getId());
        response.setDateTime(order.getDateTime());
        response.setDelivery(150);
        int sum = 0;
        List<String> names = new ArrayList<>();
        List<Long> imageIds = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Integer> detailSums = new ArrayList<>();
        for(OrderItem item: order.getItems()){
            names.add(item.getName());
            if(item.getImageId() != null)
                imageIds.add(item.getImageId());
            else imageIds.add(null);
            prices.add(item.getPrice());
            quantities.add(item.getQuantity());
            detailSums.add(item.getPrice() * item.getQuantity());
            sum += item.getPrice() * item.getQuantity();
        }
        response.setNames(names);
        response.setImageIds(imageIds);
        response.setPrices(prices);
        response.setQuantities(quantities);
        response.setDetailSums(detailSums);
        response.setSum(sum);

        return response;
    }
}
