package com.example.pj_webshop.repositories.products;

import com.example.pj_webshop.entities.models.products.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Integer> {
    List<ProductOptionGroup> findByProductPropertiesId(int propertiesId);
}
