package com.example.pj_webshop.entities.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

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

    @Column(name = "name")
    private String name;

    @Column(name = "is_required")
    private boolean isRequired;

    @Column(name = "group_modification_mode")
    @Enumerated(EnumType.STRING)
    private GroupModificationMode groupModificationMode;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "product_option_group_id")
    @JsonIgnore
    private List<ProductOption> productOptions;

}
