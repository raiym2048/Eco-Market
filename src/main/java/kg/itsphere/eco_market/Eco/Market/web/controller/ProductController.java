package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/getProducts/")
    public List<ProductResponse> findProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name
    ) {
        if((category == null || category.trim().isEmpty()) && (name == null || name.trim().isEmpty())) {
            return productService.findAll();
        } else {
            if (category != null && name != null) {
                return productService.findProductsByCategoryAndName(category, name);
            } else if (category != null) {
                return productService.findProductsByCategory(category);
            } else {
                return productService.findProductsByName(name);
            }
        }
    }
}
