package kg.itsphere.eco_market.Eco.Market.web.controller;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/findProductWithSorting/{field}")
    public List<ProductResponse> findProductsWithSorting(@PathVariable String field) {
        return productService.findProductsWithSorting(field);
    }

    @GetMapping("/findProductWithPagination/{offset}/pageSize/{pageSize}")
    public Page<Product> findProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return productService.findProductsWithPagination(offset, pageSize);
    }
    @GetMapping("/findByName/{name}")
    public ProductResponse findProductsByName(@PathVariable String name){
        return productService.findByName(name);
    }
    @GetMapping("/findByCategory/{category}")
    public List<ProductResponse> findProductsByCategory(@PathVariable Category category){
        return productService.findByCategory(category);
    }
    @GetMapping("/allProducts")
    public List<ProductResponse> getAll(){
        return productService.findAll();
    }


    @GetMapping("/allProductsBYCategoryAndName")
    public List<ProductResponse> allByCategoryAndName(@RequestParam String name,@RequestParam String category){
        return productService.findProductsByCategoryAndName(category, name);
    }


    @GetMapping("/allProductsByCategory")
    public List<ProductResponse> allByCategory(@RequestParam String category){
        return productService.findProductsByCategory(category);
    }


    @GetMapping("/allProductsByName")
    public List<ProductResponse> allByName(@RequestParam String name) {
        return productService.findProductsByName(name);
    }
}
