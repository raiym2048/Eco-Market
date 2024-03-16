package kg.itsphere.eco_market.Eco.Market.web.mapper;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;

import java.util.List;

public interface ProductMapper {
    ProductResponse toDto(Product product);
    List<ProductResponse> toDtoS(List<Product> productList);
    Product toDtoProduct(Product product, ProductRequest productRequest);
}
