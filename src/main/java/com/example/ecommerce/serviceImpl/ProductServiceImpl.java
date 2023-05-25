package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public void removeProductById(Long productId) {

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
    public Product updateProduct(Long productId,UpdateProductRequest updateProductRequest) {
        Product foundProduct = productRepository.findProductById(productId);
        if (foundProduct != null){
            foundProduct.setProductName(updateProductRequest.getProductName());
            foundProduct.setQuantity(updateProductRequest.getQuantity());
            foundProduct.setCategory(updateProductRequest.getCategory());
            foundProduct.setPricePerUnit(updateProductRequest.getPricePerUnit());
            productRepository.save(foundProduct);
        }
        return foundProduct;
    }
}
