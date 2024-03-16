package kg.itsphere.eco_market.Eco.Market.web.mapper.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductResponse toDto(Product product) {
        return null;
    }

    @Override
    public List<ProductResponse> toDtoS(List<Product> productList) {
        return null;
    }

    @Override
    public Product toDtoProduct(Product product, ProductRequest productRequest) {
        return null;
    }
}
