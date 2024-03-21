package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.BasketItemRepository;
import kg.itsphere.eco_market.Eco.Market.repository.BasketRepository;
import kg.itsphere.eco_market.Eco.Market.repository.ProductRepository;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.BasketMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final AuthService authService;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;
    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;

    @Override
    public void add(BasketRequest request, String token) {
        User user = authService.getUserFromToken(token);
        Basket basket = user.getBasket();
        Optional<Product> product = productRepository.findById(request.getProductId());
        if(product.isEmpty())
            throw new NotFoundException("Product with id: " + request.getProductId() + " - doesn't exist!", HttpStatus.NOT_FOUND);
        Optional<BasketItem> isItem = basketItemRepository.findByProductIdAndBasket(request.getProductId(), user.getBasket());
        if(isItem.isPresent()) {
            throw new BadRequestException("You already added this product to your cart!");
        }
        if(request.getQuantity() > product.get().getQuantity())
            throw new BadRequestException("There is only " + product.get().getQuantity() + " products!");
        product.get().setQuantity(product.get().getQuantity() - request.getQuantity());

        BasketItem item = new BasketItem();
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        item.setBasket(basket);

        BasketItem basketItem = basketItemRepository.saveAndFlush(item);

        List<BasketItem> items = new ArrayList<>();
        if(basket.getItems() != null) items = basket.getItems();
        items.add(basketItem);
        basket.setItems(items);
        productRepository.save(product.get());
        basketRepository.save(basket);
    }

    @Override
    public BasketResponse show(String token) {
        User user = authService.getUserFromToken(token);
        Basket basket = basketRepository.findById(user.getId()).get();
        return basketMapper.toDto(basket);
    }

    @Override
    public void delete(BasketRequest request, String token) {
        User user = authService.getUserFromToken(token);
        Basket basket = basketRepository.findById(user.getId()).get();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(request.getProductId(), basket);

        if(item.isEmpty())
            throw new BadRequestException("Product with id: " + request.getProductId() + " - doesn't exist!");
        Optional<Product> product = productRepository.findById(request.getProductId());
        product.get().setQuantity(product.get().getQuantity() + item.get().getQuantity());

        basket.getItems().remove(item.get());
        basketRepository.save(basket);
        item.get().setBasket(null);

//        Image image = item.get().getImage();
//        List<CartItem> items = image.getItems();
//        items.remove(item.get());
//        image.setItems(items);
//        imageRepository.save(image);
//        item.get().setImage(null);
        productRepository.save(product.get());
        basketItemRepository.delete(item.get());
    }

    @Override
    public void update(BasketRequest request, String token) {
        User user = authService.getUserFromToken(token);
        Basket basket = basketRepository.findById(user.getId()).get();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(request.getProductId(), basket);
        if(item.isEmpty())
            throw new BadRequestException("Product with id: " + request.getProductId() + " - doesn't exist!");
        Optional<Product> product = productRepository.findById(request.getProductId());
        int allSum = product.get().getQuantity() + item.get().getQuantity();
        if(request.getQuantity() > allSum)
            throw new BadRequestException("There is only " + allSum + " products!");
        item.get().setQuantity(request.getQuantity());
        product.get().setQuantity(allSum - request.getQuantity());
        productRepository.save(product.get());
        basketItemRepository.save(item.get());
    }
}
