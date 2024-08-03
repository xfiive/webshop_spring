package com.example.pj_webshop.entities.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductOptionGroupDTO {
    private int productOptionGroupId;
    private int productPropertiesId;
    private String name;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private String groupModificationMode;
    private String availableOptionsState;
    private List<ProductOptionDTO> productOptions;
}
