package com.example.pj_webshop.entities.models.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProductPropertiesDTO {
    private int productPropertiesId;
    private String description;
    private ProductDTO product;
    private List<ProductOptionGroupDTO> productOptionGroups;
}
