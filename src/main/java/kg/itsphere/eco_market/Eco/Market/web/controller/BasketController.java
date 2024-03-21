package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.Basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.Basket.BasketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;

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
        basketService.delete(request, token);
        return "Product deleted successfully!";
    }

    @PutMapping("/update")
    public String update(@RequestBody BasketRequest request, @RequestHeader("Authorization") String token){
        basketService.update(request, token);
        return "Quantity updated successfully!";
    }
}
