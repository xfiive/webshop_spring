/*
package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.models.products.Product;
import com.example.pj_webshop.repositories.products.ProductRepository;
import com.example.pj_webshop.services.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllProducts() {
        Product product1 = new Product(1, "Laptop", "Intel Core i7, 16GB RAM, 512GB SSD", new BigDecimal("1200.00"), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wcAAgIBAMc5+UAAAAAASUVORK5CYII=");
        Product product2 = new Product(2, "Smartphone", "5G, 128GB Storage, 8GB RAM", new BigDecimal("800.00"), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wcAAgIBAMc5+UAAAAAASUVORK5CYII=");
        Product product3 = new Product(3, "Headphones", "Noise Cancelling, Wireless", new BigDecimal("150.00"), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wcAAgIBAMc5+UAAAAAASUVORK5CYII=");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        List<Product> products = productService.findAllProducts();
        assertEquals(3, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findProductById() {
        Product product = new Product(1, "Laptop", "Intel Core i7, 16GB RAM, 512GB SSD", new BigDecimal("1200.00"), "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/wcAAgIBAMc5+UAAAAAASUVORK5CYII=");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findProductById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getProductId(), foundProduct.get().getProductId());
        verify(productRepository, times(1)).findById(1);
    }
}
*/
