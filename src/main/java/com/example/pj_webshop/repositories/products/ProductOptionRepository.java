package com.example.pj_webshop.repositories.products;

import com.example.pj_webshop.entities.models.products.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    List<ProductOption> findByGroupId(int groupId);
}
