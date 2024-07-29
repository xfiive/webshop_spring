package com.example.pj_webshop.entities.models.products;

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

    @Column(name = "product_name", length = 10485760)
    private String productName;

    @Column(name = "properties_id", length = 10485760)
    private int productPropertiesId;

    @Column(name = "product_decsription", length = 10485760)
    private String productDescription;

    @Column(precision = 7, scale = 2, name = "price")
    private BigDecimal price;

    @Column(name = "product_image", columnDefinition = "TEXT", length = 10485760)
    private String productImage;
}
