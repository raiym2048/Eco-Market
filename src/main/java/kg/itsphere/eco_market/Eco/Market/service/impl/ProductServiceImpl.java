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
        return productMapper.toDtoS(productRepository.findAll());
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

    @Override
    public List<ProductResponse> findProductsWithSorting(String field) {
        return productMapper.toDtoS(productRepository.findAll(Sort.by(field)));
    }

    @Override
    public Page<Product> findProductsWithPagination(int offset, int pageSize) {
        return productRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public ProductResponse findByName(String name) {
        Optional<Product> product =productRepository.findByName(name);
        if(product.isEmpty())
            throw new NotFoundException("Product with name "+ name + " wasn't found !", HttpStatus.NOT_FOUND);
        return productMapper.toDto(product.get());
    }

    @Override
    public List<ProductResponse> findByCategory(Category category) {
        List<Product> products =productRepository.findAllByCategory(category);
        if(category.equals(""))
            throw new NotFoundException("Category with the name "+ category+" wasn't found! ", HttpStatus.NOT_FOUND);
        return productMapper.toDtoS(products);
    }


    private void checker(Optional<Product> product, String name) {
        if(product.isEmpty()) {
            throw new NotFoundException("Product with name \"" + name + "\" not found", HttpStatus.NOT_FOUND);
        }
    }
}
