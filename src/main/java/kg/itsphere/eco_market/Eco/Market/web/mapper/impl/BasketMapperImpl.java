package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.repository.ProductRepository;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.BasketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasketMapperImpl implements BasketMapper {
    private final ProductRepository productRepository;

    // Response without images
    @Override
    public BasketResponse toDto(Basket basket) {
        BasketResponse response = new BasketResponse();
        List<String> titles = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
//        List<String> imageNames = new ArrayList<>();
        int sum = 0;
        int delivery = 150;
        List<BasketItem> items = basket.getItems();
        for(BasketItem item: items) {
            Product product = productRepository.findById(item.getProductId()).get();
            titles.add(product.getName());
            prices.add(product.getPrice());
            quantities.add(item.getQuantity());
            sum += product.getPrice() * item.getQuantity();
//            if(item.getImage() != null)
//                imageNames.add(item.getImage().getName());
        }
        response.setTitles(titles);
        response.setPrices(prices);
        response.setQuantities(quantities);
        response.setSum(sum);
        response.setDelivery(delivery);
        response.setTotal(sum + delivery);
//        response.setImageNames(imageNames);

        return response;
    }
}
