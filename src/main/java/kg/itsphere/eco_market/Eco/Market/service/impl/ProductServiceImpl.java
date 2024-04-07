package kg.itsphere.eco_market.Eco.Market.service.impl;

import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Category;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Image;
import kg.itsphere.eco_market.Eco.Market.domain.entity.product.Product;
import kg.itsphere.eco_market.Eco.Market.domain.exception.BadCredentialsException;
import kg.itsphere.eco_market.Eco.Market.domain.exception.NotFoundException;
import kg.itsphere.eco_market.Eco.Market.repository.ImageRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ImageRepository imageRepository;

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
        if(productRepository.findAll().isEmpty()) {
            throw new NotFoundException("Found nothing", HttpStatus.NOT_FOUND);
        } else {
            return productMapper.toDtoS(productRepository.findAll());
        }
    }

    @Override
    public void attachImageToProduct(String productName, String imageName) {
        Optional<Product> product = productRepository.findByName(productName);
        checker(product, productName);
        Optional<Image> image = imageRepository.findByName(imageName);
        if(image.isEmpty()) {
            throw new NotFoundException("Image with name=\"" + imageName + "\" not found", HttpStatus.NOT_FOUND);
        }
        product.get().setImage(image.get());
        productRepository.save(product.get());
    }

    private void checker(Optional<Product> product, String name) {
        if(product.isEmpty()) {
            throw new NotFoundException("Product with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public List<ProductResponse> findProductsByCategory(String category) {
        List<Product> productsSortedByCategory = productRepository.findAllByCategory(Category.valueOf(category));
        if(productsSortedByCategory.isEmpty()) {
            throw new NotFoundException("Found nothing", HttpStatus.NOT_FOUND);
        } else {
            return productMapper.toDtoS(productsSortedByCategory);
        }
    }

    @Override
    public List<ProductResponse> findProductsByName(String name) {
        List<Product> sortedProducts = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getName().contains(name)) {
                sortedProducts.add(product);
            }
        }
        if(sortedProducts.isEmpty()) {
            throw new NotFoundException("Found nothing", HttpStatus.NOT_FOUND);
        } else {
            return productMapper.toDtoS(sortedProducts);
        }
    }

    @Override
    public List<ProductResponse> findProductsByCategoryAndName(String category, String name) {
        List<Product> sortedProducts = new ArrayList<>();
        for(Product product : productRepository.findAll()) {
            if(product.getName().startsWith(name) && product.getCategory().equals(Category.valueOf(category))) {
                sortedProducts.add(product);
            }
        }
        if(sortedProducts.isEmpty()) {
            throw new NotFoundException("Found nothing", HttpStatus.NOT_FOUND);
        } else {
            return productMapper.toDtoS(sortedProducts);
        }
    }


}
