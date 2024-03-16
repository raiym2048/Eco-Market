package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.service.UserService;
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
    private final UserService service;

    // It's for adding the product
    @PostMapping("/add")
    public void add(@RequestBody ProductRequest productRequest) {
        productService.add(productRequest);
    }

    // It's for updating the product. Admin will find the product with its name, and he can update all fields of the product
    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody ProductRequest productRequest) {
        productService.updateByName(name, productRequest);
    }

    // It's for deleting the product. Also, admin will delete the product with its name
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        productService.deleteByName(name);
    }




    // With this endpoint admin will see every user's data
    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    // With this endpoint admin can see only one person's data
    @GetMapping("/findByEmail/{email}")
    public UserResponse findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    // This endpoint for changing the role of users. Admin can give to others role "admin"
    @PatchMapping("/updateRole/{userEmail}")
    public void controlUserRole(@PathVariable String userEmail, @RequestParam Map<String, Object> fields) {
        service.controlUserRole(userEmail, fields);
    }

    // it's just for testing
    @PostMapping("/register")
    public void register(@RequestBody UserRequest userRequest) {
        service.register(userRequest);
    }
}
