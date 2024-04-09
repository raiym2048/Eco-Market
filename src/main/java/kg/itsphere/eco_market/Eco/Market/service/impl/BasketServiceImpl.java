package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.config.JwtService;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.entity.user.User;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Basket;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.BasketItem;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.Order;
import kg.itsphere.eco_market.Eco.Market.domain.entity.userInfo.OrderItem;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadRequestException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.*;
import kg.itsphere.eco_market.Eco.Market.service.AuthService;
import kg.itsphere.eco_market.Eco.Market.service.BasketService;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.basket.BasketResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.BasketMapper;
import kg.itsphere.eco_market.Eco.Market.web.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class BasketServiceImpl implements BasketService {
    private final AuthService authService;
    private final ProductRepository productRepository;
    private final BasketItemRepository basketItemRepository;
    private final BasketRepository basketRepository;
    private final BasketMapper basketMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Override
    public void add(BasketRequest request, String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
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

        BasketItem item = new BasketItem();
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        item.setBasket(basket);

        BasketItem basketItem = basketItemRepository.saveAndFlush(item);

        List<BasketItem> items = new ArrayList<>();
        if(basket.getItems() != null) items = basket.getItems();
        items.add(basketItem);
        basket.setItems(items);
        basketRepository.save(basket);
    }

    @Override
    public BasketResponse show(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        return basketMapper.toDto(basket);
    }

    @Override
    public void delete(BasketItem item, Basket basket) {
        basket.getItems().remove(item);
        basketRepository.save(basket);
        item.setBasket(null);

        basketItemRepository.delete(item);
    }

    @Override
    public void addOne(String token, Long id) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(id, basket);
        if(item.isEmpty()) {
            throw new BadRequestException("Product with id: " + id + " - doesn't exist!");
        }
        Optional<Product> product = productRepository.findById(id);
        int newSum = item.get().getQuantity() + 1;
        if(product.get().getQuantity() < newSum) {
            throw new BadRequestException("There is only " + product.get().getQuantity() + " products!");
        }
        item.get().setQuantity(newSum);
        basketItemRepository.save(item.get());
    }

    @Override
    public void decreaseOne(String token, Long id) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        Optional<BasketItem> item = basketItemRepository.findByProductIdAndBasket(id, basket);
        if(item.isEmpty()) {
            throw new BadRequestException("Product with id: " + id + " - doesn't exist!");
        }
        int newSum = item.get().getQuantity() - 1;
        if(newSum < 1) {
            delete(item.get(), basket);
            return;
        }
        item.get().setQuantity(newSum);
        basketItemRepository.save(item.get());
    }

    @Override
    public void check(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        List<BasketItem> items = basket.getItems();
        int sum = 0;
        for(BasketItem item: items){
            Optional<Product> product = productRepository.findById(item.getProductId());
            sum += item.getQuantity() * product.get().getPrice();
        }
        if(sum < 300)
            throw new BadRequestException("The order can be made with a purchase over 300 soms");
    }

    @Override
    public OrderResponse buy(OrderRequest request, String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        if(request.getNumber() == null || request.getComment() == null
        || request.getAddress() == null || request.getOrientation() == null) {
            throw new BadRequestException("Fill in all the fields!");
        }

        if(basket.getItems().isEmpty()) {
            throw new BadRequestException("Your basket is empty!");
        }
        for(BasketItem item: basket.getItems()){
            Product product = productRepository.findById(item.getProductId()).get();
            if(item.getQuantity()>product.getQuantity())
                throw new BadRequestException("There is only " + product.getQuantity() + " products of " + product.getName());
        }


        user.setAddress(request.getAddress());
        user.setComment(request.getComment());
        user.setPhoneNumber(request.getNumber());
        user.setOrientation(request.getOrientation());

        Order order = new Order();
        order.setUser(user);
        order.setDateTime(LocalDateTime.now());
        Order saveOrder = orderRepository.saveAndFlush(order);
        List<Order> orders = user.getOrders();
        orders.add(saveOrder);
        user.setOrders(orders);
        userRepository.save(user);

        List<OrderItem> orderItems = new ArrayList<>();
        List<BasketItem> items = basket.getItems();
        for(BasketItem item: items){
            Product product = productRepository.findById(item.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(saveOrder);
            if(product.getImage() != null)
                orderItem.setImageId(product.getImage().getId());
            else orderItem.setImageId(null);
            orderItems.add(orderItemRepository.saveAndFlush(orderItem));
            item.setBasket(null);
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }
        basket.setItems(null);
        basketRepository.save(basket);
        basketItemRepository.deleteAll(items);

        saveOrder.setItems(orderItems);
        Order order1 = orderRepository.saveAndFlush(saveOrder);
        return orderMapper.toDto(order1);
    }

    @Override
    public void clear(String token) {
        if(token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String userEmail = jwtService.getUserName(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User with email" + userEmail + " not found", HttpStatus.NOT_FOUND));
        Basket basket = user.getBasket();
        for(BasketItem item: basket.getItems()) item.setBasket(null);
        List<BasketItem> items = basket.getItems();
        basket.setItems(null);
        basketRepository.save(basket);
        basketItemRepository.deleteAll(items);
    }
}
