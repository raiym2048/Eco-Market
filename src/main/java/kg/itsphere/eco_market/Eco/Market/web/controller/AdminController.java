package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.InformationService;
import kg.itsphere.eco_market.Eco.Market.service.OrderService;
import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.service.UserService;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.info.InformationResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderDetailResponseForAdmin;
import kg.itsphere.eco_market.Eco.Market.web.dto.order.OrderResponse;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;
    private final InformationService informationService;
    private final OrderService orderService;

    // It's for adding the product
    @PostMapping("/product/add")
    public void add(@RequestBody ProductRequest productRequest) {
        productService.add(productRequest);
    }

    // It's for updating the product. Admin will find the product with its name, and he can update all fields of the product
    @PutMapping("/product/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody ProductRequest productRequest) {
        productService.updateByName(name, productRequest);
    }

    // It's for deleting the product. Also, admin will delete the product with its name
    @DeleteMapping("/product/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        productService.deleteByName(name);
    }

    // Admin will attach photo to product with this endpoint
    @PostMapping("/product/attachImageToProduct/{productName}/image/{imageName}")
    public void attachImageToProduct(@PathVariable String productName, @PathVariable String imageName) {
        productService.attachImageToProduct(productName, imageName);
    }




    // With this endpoint admin will see every user's data
    @GetMapping("/user/getAll")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    // With this endpoint admin can see only one person's data
    @GetMapping("/user/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    // This endpoint for changing the role of users. Admin can give to others role "admin"
    @PatchMapping("/user/updateRole/{userEmail}")
    public void controlUserRole(@PathVariable String userEmail, @RequestParam Map<String, Object> fields) {
        userService.controlUserRole(userEmail, fields);
    }

    // it's just for testing
    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        userService.register(userRequest);
    }




    // These are to work with information
    @PostMapping("/info/creatInfo")
    public void createInfo(@RequestBody InformationRequest informationRequest) {
        informationService.createInfo(informationRequest);
    }

    @PutMapping("/info/updateById/{id}")
    void updateById(@PathVariable Long id, @RequestBody InformationRequest informationRequest) {
        informationService.updateById(id, informationRequest);
    }

    @DeleteMapping("/info/deleteById/{id}")
    void deleteById(@PathVariable Long id) {
        informationService.deleteById(id);
    }





    @GetMapping("/order/getAll")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order/getPersonOrder/{id}")
    OrderDetailResponseForAdmin getPersonOrder(@PathVariable Long id) {
        return orderService.getPersonOrder(id);
    }
}
