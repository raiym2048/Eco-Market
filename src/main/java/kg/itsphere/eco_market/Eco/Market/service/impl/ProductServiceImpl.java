package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadCredentialsException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.ProductRepository;
import kg.itsphere.eco_market.Eco.Market.service.ProductService;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductRequest;
import kg.itsphere.eco_market.Eco.Market.web.dto.product.ProductResponse;
import kg.itsphere.eco_market.Eco.Market.web.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public void add(ProductRequest productRequest) {
        if(productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new BadCredentialsException("Product with name \"" + productRequest.getName() + "\" is already exist");
        }
        Product product = new Product();
        productRepository.save(productMapper.toDtoProduct(product, productRequest));
    }

    @Override
    public void updateByName(String name, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findByName(name);
        checker(product, name);
        productRepository.save(productMapper.toDtoProduct(product.get(), productRequest));
    }

    @Override
    public void deleteByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        checker(product, name);
        productRepository.deleteByName(name);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productMapper.toDtoS(productRepository.findAll());
    }

    @Override
    public List<ProductResponse> findProductsWithSorting(String field) {
        return productMapper.toDtoS(productRepository.findAll(Sort.by(field)));
    }

    @Override
    public Page<Product> findProductsWithPagination(int offset, int pageSize) {
        return productRepository.findAll(PageRequest.of(offset, pageSize));
    }


    private void checker(Optional<Product> product, String name) {
        if(product.isEmpty()) {
            throw new NotFoundException("Product with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
