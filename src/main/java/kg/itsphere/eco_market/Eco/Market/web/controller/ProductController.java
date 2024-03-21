package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public String add(@RequestBody ProductRequest productRequest){
        productService.add(productRequest);
        return "Product added successfully";
    }

    @GetMapping("/all")
    public List<ProductResponse> all(){
        return productService.findAll();
    }

}
