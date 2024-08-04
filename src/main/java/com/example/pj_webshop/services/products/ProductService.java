package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.products.Product;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.repositories.DeliveryRepository;
import com.example.pj_webshop.repositories.OrderRepository;
import com.example.pj_webshop.repositories.interim.ClientOrderRepository;
import com.example.pj_webshop.repositories.interim.OrderedProductRepository;
import com.example.pj_webshop.repositories.products.ProductOptionGroupRepository;
import com.example.pj_webshop.repositories.products.ProductOptionRepository;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import com.example.pj_webshop.repositories.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ProductPropertiesRepository productPropertiesRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductOptionRepository productOptionRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        var productOpt = this.findProductById(id);
        if (productOpt.isEmpty()) {
            log.error("product opt found is empty");
            return false;
        }

        log.info("Product found exists");

        var orderedProducts = orderedProductRepository.findAllByProduct(productOpt.get());
        orderedProducts.forEach(orderedProduct -> {
            var order = orderedProduct.getOrder();
            var clientOrderOpt = clientOrderRepository.findByOrderId(order.getOrderId());
            var delivery = order.getDelivery();

            orderedProductRepository.delete(orderedProduct);
            clientOrderOpt.ifPresent(clientOrderRepository::delete);
            if (delivery != null) deliveryRepository.delete(delivery);
            orderRepository.delete(order);
        });

        var productPropertiesOpt = productPropertiesRepository.findById(productOpt.get().getProductPropertiesId());
        if (productPropertiesOpt.isPresent()) {
            var productProperties = productPropertiesOpt.get();
            var productOptionGroups = productProperties.getProductOptionGroups();

            productOptionGroups.forEach(group -> {
                var productOptions = group.getProductOptions();
                productOptionRepository.deleteAll(productOptions);
                productOptionGroupRepository.delete(group);
            });

            productPropertiesRepository.delete(productProperties);
        }

        productRepository.delete(productOpt.get());

        if (productRepository.findById(id).isEmpty()) {
            log.info("Product successfully deleted with ID: {}", id);
            return true;
        } else {
            log.error("Failed to delete product with ID: {}", id);
            return false;
        }
    }

}
