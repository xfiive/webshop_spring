package com.example.pj_webshop.entities.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOptionDTO {
    private int productOptionId;
    private int groupId;
    private String name;
    private String image;
    private BigDecimal price;
    private boolean accessible;
}
