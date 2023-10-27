package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.FindAllRequest;
import com.example.ecommerce.dto.request.RatingRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.dto.response.RatingResponse;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    ModelMapper mapper = new ModelMapper();

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product addProduct(AddProductRequest addProductRequest) {
        Product product = mapper.map(addProductRequest,Product.class);
        return productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void removeProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(Long productId) {
       return productRepository.findProductById(productId);
    }

    @Override
    public Page<Product> findAllProductsWithPaginationAndSortingWithDirection(FindAllRequest findAllProductRequest) {
        return productRepository
                .findAll(PageRequest.of(findAllProductRequest.getOffset(), findAllProductRequest.getPageSite())
                        .withSort(Sort.by(Sort.Direction
                                .fromString(findAllProductRequest.getDirection()),findAllProductRequest.getField())));

    }
    @Override
    public Product updateProduct(Long productId,UpdateProductRequest updateProductRequest) {
        Product foundProduct = productRepository.findProductById(productId);
        if (foundProduct != null) {
            foundProduct.setProductName(updateProductRequest.getProductName());
            foundProduct.setQuantity(updateProductRequest.getQuantity());
            foundProduct.setCategory(updateProductRequest.getCategory());
            foundProduct.setPricePerUnit(updateProductRequest.getPricePerUnit());
            productRepository.save(foundProduct);
        }
        return foundProduct;
    }
    }


