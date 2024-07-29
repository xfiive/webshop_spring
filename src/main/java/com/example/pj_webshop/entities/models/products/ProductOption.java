package com.example.pj_webshop.entities.models.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product_options")
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productOptionId;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "name", length = 10485760)
    private String name;

    @Column(name = "image_base64", length = 10485760)
    private String image;

    @Column(precision = 7, scale = 3, name = "price")
    private BigDecimal price;

    @Column(name = "accessible")
    private boolean accessible;
}
