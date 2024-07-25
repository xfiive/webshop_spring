package com.example.pj_webshop.repositories.products;

import com.example.pj_webshop.entities.models.products.ProductProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPropertiesRepository extends JpaRepository<ProductProperties, Integer> {

    @Query("SELECT p FROM ProductProperties p LEFT JOIN FETCH p.productOptionGroups WHERE p.productPropertiesId = :id")
    Optional<ProductProperties> findWithGroupsByProductPropertiesId(int id);
}
