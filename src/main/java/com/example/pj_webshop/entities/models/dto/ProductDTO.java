package com.example.pj_webshop.entities.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private int productId;
    private String productName;
    private int productPropertiesId;
    private String productDescription;
    private BigDecimal price;
    private String productImage;
}
