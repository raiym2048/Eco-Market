package kg.itsphere.eco_market.Eco.Market.service;

import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;

public interface ProductService {
    void add(ProductRequest productRequest);
    void updateByName(String name, ProductRequest productRequest);
    void deleteByName(String name);
}
