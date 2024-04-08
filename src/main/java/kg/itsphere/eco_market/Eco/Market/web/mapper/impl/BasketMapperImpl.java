package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.repository.ProductRepository;
import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketProductResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.BasketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BasketMapperImpl implements BasketMapper {
    private final ProductRepository productRepository;
    private final BasketService basketService;

    @Override
    public BasketResponse toDto(Basket basket) {
        BasketResponse response = new BasketResponse();
        List<BasketProductResponse> basketProductResponses = new ArrayList<>();
        int sum = 0;
        int delivery = 150;
        List<BasketItem> items = basket.getItems();
        for(int i = 0; i < items.size(); i++){
            BasketProductResponse productResponse = new BasketProductResponse();
            BasketItem item = items.get(i);
            Optional<Product> product = productRepository.findById(item.getProductId());
            if(product.isEmpty()){
                basketService.delete(item, basket);
                continue;
            }
            if(product.get().getImage() != null) {
                productResponse.setImagePath(product.get().getImage().getPath());
            } else {
                productResponse.setImagePath(null);
            }
            productResponse.setProductID(product.get().getId());
            productResponse.setTitle(product.get().getName());
            productResponse.setPrice(product.get().getPrice());
            productResponse.setQuantity(item.getQuantity());
            sum += product.get().getPrice() * item.getQuantity();
            basketProductResponses.add(productResponse);
        }
        response.setProductResponses(basketProductResponses);
        response.setSum(sum);
        response.setDelivery(delivery);
        response.setTotal(sum + delivery);
        return response;
    }
}
