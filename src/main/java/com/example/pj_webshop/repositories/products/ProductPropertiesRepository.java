package com.example.pj_webshop.repositories.products;

import com.example.pj_webshop.entities.models.products.ProductProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertiesRepository extends JpaRepository<ProductProperties, Integer> {
}
