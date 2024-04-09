package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.config.JwtService;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.BasketItemRepository;
import kg.itsphere.eco_market.Eco.Market.repository.BasketRepository;
import kg.itsphere.eco_market.Eco.Market.repository.UserRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.auth.MyData;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/add")
    public MyData add(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        basketService.add(request, token);
        MyData data = new MyData();
        data.setMessage("Product added to Basket successfully");
        return data;
    }

    @GetMapping("/show")
    public BasketResponse show(@RequestHeader("Authorization") String token){
        return basketService.show(token);
    }

    @DeleteMapping("/delete")
    public MyData delete(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        if(token.startsWith("Bearer"))
            token = token.substring(7);
        if(request.getProductId() == null)
            throw new BadRequestException("productId shouldn't be null");
        Long id = request.getProductId();
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = basketRepository.findById(user.getId()).get();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(id, basket);
        if(item.isEmpty())
            throw new BadRequestException("Product with id: " + id + " - doesn't exist!");
        basketService.delete(item.get(), basket);
        MyData data = new MyData();
        data.setMessage("Product deleted successfully!");
        return data;
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProductQuantity(@RequestHeader("Authorization") String token,
                                      @PathVariable Long id,
                                      @RequestParam String action) {
        if ("plus".equals(action)) {
            basketService.addOne(token, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else if ("minus".equals(action)) {
            basketService.decreaseOne(token, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/clear")
    public MyData clear(@RequestHeader("Authorization")String token){
        basketService.clear(token);
        MyData data = new MyData();
        data.setMessage("Your basket is empty!");
        return data;
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
