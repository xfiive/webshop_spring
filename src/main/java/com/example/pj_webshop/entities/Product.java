package com.example.pj_webshop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(length = 100, name = "product_name")
    private String productName;

    @Column(length = 1000, name = "product_properties")
    private String productProperties;

    @Column(precision = 7, scale = 2, name = "price")
    private BigDecimal price;

    @Column(name = "product_image", columnDefinition = "TEXT")
    private String productImage;
}
