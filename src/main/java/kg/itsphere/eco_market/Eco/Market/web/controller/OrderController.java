package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.OrderService;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    public List<OrderResponse> all(@RequestHeader("Authorization")String token){
        return orderService.all(token);
    }

    @GetMapping("/{id}")
    public OrderDetailResponse detail(@RequestHeader("Authorization")String token, @PathVariable Long id){
        return orderService.detail(token, id);
    }
}
