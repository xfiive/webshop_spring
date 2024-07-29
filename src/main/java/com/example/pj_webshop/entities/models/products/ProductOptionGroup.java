package com.example.pj_webshop.entities.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "product_option_group")
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productOptionGroupId;

    @Column(name = "properties_id")
    private int productPropertiesId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_required")
    private boolean isRequired;

    @Column(name = "group_modification_mode")
    @Enumerated(EnumType.STRING)
    private GroupModificationMode groupModificationMode;

    @Column(name = "available_options_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailableOptionsState availableOptionsState;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_option_group_id")
    @JsonIgnore
    private List<ProductOption> productOptions;
}