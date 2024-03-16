package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    // it's for admin
    @PostMapping("/add")
    public void add(@RequestBody ProductRequest productRequest) {
        productService.add(productRequest);
    }

    // it's for admin
    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name, @RequestBody ProductRequest productRequest) {
        productService.updateByName(name, productRequest);
    }

    // it's for admin
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        productService.deleteByName(name);
    }
}
