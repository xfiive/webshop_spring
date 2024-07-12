package com.example.pj_webshop.repositories.interim;

import com.example.pj_webshop.entities.interim.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Integer> {
    List<OrderedProduct> findAllByOrder_OrderId(int orderId);
}
