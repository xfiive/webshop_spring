package com.example.pj_webshop.repositories.interim;

import com.example.pj_webshop.entities.interim.OrderedProduct;
import com.example.pj_webshop.entities.models.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {
    List<OrderedProduct> findAllByOrder_OrderId(int orderId);

    Optional<OrderedProduct> findByProduct(Product product);
}
