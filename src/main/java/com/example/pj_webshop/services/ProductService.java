package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.Product;
import com.example.pj_webshop.repositories.ProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProductsByName(String name) {
        return productRepository.findAllByProductName(name);
    }

    public Optional<Product> addNew(@NotNull Product product) {
        var existingProduct = this.findProductById(product.getProductId());

        if (existingProduct.isPresent()) {
            return Optional.empty();
        }

        productRepository.save(product);

        return Optional.of(product);
    }

    public Optional<Product> updateProduct(int id, Product product) {
        var existingProductOpt = this.findProductById(id);
        if (existingProductOpt.isEmpty()) {
            return Optional.empty();
        }

        var existingProduct = existingProductOpt.get();

        existingProduct.setProductId(product.getProductId());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductProperties(product.getProductProperties());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductImage(product.getProductImage());

        productRepository.save(existingProduct);

        return Optional.of(existingProduct);
    }

    public boolean deleteProduct(int id) {
        var existingProduct = this.findProductById(id);

        if (existingProduct.isEmpty()) {
            return false;
        }

        productRepository.deleteById(id);

        return true;
    }

}