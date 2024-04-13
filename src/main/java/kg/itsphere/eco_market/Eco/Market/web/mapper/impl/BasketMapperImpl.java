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
    public BasketProductResponse toDto(Product product, int quantity) {
        BasketProductResponse response = new BasketProductResponse();
        if(product.getImage() != null) {
            response.setImagePath(product.getImage().getPath());
        } else {
            response.setImagePath(null);
        }
        response.setProductID(product.getId());
        response.setTitle(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(quantity);
        response.setSubTotal(quantity * product.getPrice());

        return response;
    }

    @Override
    public BasketResponse toDtoS(Basket basket) {
        BasketResponse response = new BasketResponse();
        List<BasketProductResponse> basketProductResponses = new ArrayList<>();
        int sum = 0;
        int delivery = 150;
        List<BasketItem> items = basket.getItems();
        for (BasketItem item : items) {
            Optional<Product> product = productRepository.findById(item.getProductId());
            if (product.isEmpty()) {
                basketService.delete(item, basket);
                continue;
            }
            BasketProductResponse productResponse = toDto(product.get(), item.getQuantity());

//            if(product.get().getImage() != null) {
//                productResponse.setImagePath(product.get().getImage().getPath());
//            } else {
//                productResponse.setImagePath(null);
//            }
//            productResponse.setProductID(product.get().getId());
//            productResponse.setTitle(product.get().getName());
//            productResponse.setPrice(product.get().getPrice());
//            productResponse.setQuantity(item.getQuantity());
//            productResponse.setSubTotal(item.getQuantity() * product.get().getPrice());
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
