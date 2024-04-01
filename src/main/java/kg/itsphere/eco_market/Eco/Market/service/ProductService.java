package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    void add(ProductRequest productRequest);
    void updateByName(String name, ProductRequest productRequest);
    void deleteByName(String name);
    List<ProductResponse> findAll();
    void attachImageToProduct(String productName, String imageName);

    List<ProductResponse> findProductsByCategory(String category);
    List<ProductResponse> findProductsByName(String name);
    List<ProductResponse> findProductsByCategoryAndName(String category, String name);

}
