package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.OrderRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.OrderService;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponseForAdmin;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final AuthService authService;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> all(String token) {
        User user = authService.getUserFromToken(token);
        return orderMapper.toDtos(user.getOrders());
    }

    @Override
    public OrderDetailResponse detail(String token, Long id) {
        User user = authService.getUserFromToken(token);
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty())
            throw new NotFoundException("Order with id: " + id + " - not found!", HttpStatus.NOT_FOUND);
        return orderMapper.toDetailDto(order.get());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderMapper.toDtos(orderRepository.findAll());
    }

    @Override
    public OrderDetailResponseForAdmin getPersonOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty()) {
            throw new NotFoundException("Order with id: " + id + " - not found!", HttpStatus.NOT_FOUND);
        }
        return orderMapper.toDtoAdmin(order.get());
    }
}
