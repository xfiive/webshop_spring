package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.products.Product;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import com.example.pj_webshop.repositories.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductPropertiesService {

    private final ProductPropertiesRepository repository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductPropertiesRepository productPropertiesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductProperties> findAll() {
        return repository.findAll();
    }

    public Optional<ProductProperties> findById(int id) {
        return repository.findById(id);
    }

    public Optional<ProductProperties> addNew(@NotNull ProductProperties properties) {
        if (repository.existsById(properties.getProductPropertiesId())) {
            return Optional.empty();
        }

        Product product = properties.getProduct();
        if (product != null && !productRepository.existsById(product.getProductId())) {
            product = productRepository.saveAndFlush(product);
            properties.setProduct(product);
        }

        ProductProperties mergedProperties = entityManager.merge(properties);
        repository.saveAndFlush(mergedProperties);
        return Optional.of(mergedProperties);
    }

    @Transactional
    public Optional<ProductProperties> update(int id, ProductProperties newProperties) {
        return repository.findById(id).map(existingProperties -> {
            existingProperties.setDescription(newProperties.getDescription());
            existingProperties.setProduct(newProperties.getProduct());

            if (existingProperties.getProductOptionGroups() == null) {
                existingProperties.setProductOptionGroups(new ArrayList<>());
            }

            existingProperties.getProductOptionGroups().clear();
            if (newProperties.getProductOptionGroups() != null) {
                existingProperties.getProductOptionGroups().addAll(newProperties.getProductOptionGroups());
            }

            return repository.saveAndFlush(existingProperties);
        });
    }

    public boolean delete(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
