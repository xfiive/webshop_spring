package com.example.pj_webshop.entities.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductOptionGroupDTO {
    private int productOptionGroupId;
    private String name;
    private boolean isRequired;
    private String groupModificationMode;
    private List<ProductOptionDTO> productOptions;
}
