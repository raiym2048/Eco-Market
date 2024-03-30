package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.repository.BasketItemRepository;
import kg.itsphere.eco_market.Eco.Market.repository.BasketRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;
    private final AuthService authService;
    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;

    @PostMapping("/add")
    public String add(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        basketService.add(request, token);
        return "Product added to Basket successfully";
    }

    @GetMapping("/show")
    public BasketResponse show(@RequestHeader("Authorization") String token){
        return basketService.show(token);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        User user = authService.getUserFromToken(token);
        Basket basket = basketRepository.findById(user.getId()).get();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(request.getProductId(), basket);
        if(item.isEmpty())
            throw new BadRequestException("Product with id: " + request.getProductId() + " - doesn't exist!");
        basketService.delete(item.get(), basket);
        return "Product deleted successfully!";
    }

    @PutMapping("/update")
    public String update(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        basketService.update(request, token);
        return "Quantity updated successfully!";
    }

    @DeleteMapping("/clear")
    public String clear(@RequestHeader("Authorization")String token){
        basketService.clear(token);
        return "Your basket is empty!";
    }

    @GetMapping("/check")
    public void check(@RequestHeader("Authorization") String token){
        basketService.check(token);
    }

    @PostMapping("/buy")
    public OrderResponse buy(@RequestBody OrderRequest request, @RequestHeader("Authorization") String token){
        return basketService.buy(request, token);
    }
}
