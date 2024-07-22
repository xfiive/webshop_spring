package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.products.Product;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import com.example.pj_webshop.repositories.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductPropertiesRepository productPropertiesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setProductPropertiesRepository(ProductPropertiesRepository productPropertiesRepository) {
        this.productPropertiesRepository = productPropertiesRepository;
    }

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

    public Optional<ProductProperties> findProductPropertiesById(int id) {
        return productPropertiesRepository.findById(id);
    }

    public Optional<Product> addNew(@NotNull Product product) {
        var existingProduct = this.findProductById(product.getProductId());

        if (existingProduct.isPresent()) {
            return Optional.empty();
        }

        Product mergedProduct = entityManager.merge(product);
        productRepository.save(mergedProduct);

        return Optional.of(mergedProduct);
    }

    public Optional<Product> updateProduct(int id, Product product) {
        var existingProductOpt = this.findProductById(id);
        if (existingProductOpt.isEmpty()) {
            return Optional.empty();
        }

        var existingProduct = existingProductOpt.get();

        existingProduct.setProductPropertiesId(product.getProductPropertiesId());
        existingProduct.setProductId(product.getProductId());
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductImage(product.getProductImage());

        Product mergedProduct = entityManager.merge(existingProduct);
        productRepository.save(mergedProduct);

        return Optional.of(mergedProduct);
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
