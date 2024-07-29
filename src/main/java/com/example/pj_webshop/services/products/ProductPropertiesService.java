package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.dto.ProductDTO;
import com.example.pj_webshop.entities.models.dto.ProductOptionDTO;
import com.example.pj_webshop.entities.models.dto.ProductOptionGroupDTO;
import com.example.pj_webshop.entities.models.dto.ProductPropertiesDTO;
import com.example.pj_webshop.entities.models.products.Product;
import com.example.pj_webshop.entities.models.products.ProductOption;
import com.example.pj_webshop.entities.models.products.ProductOptionGroup;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.repositories.products.ProductOptionGroupRepository;
import com.example.pj_webshop.repositories.products.ProductOptionRepository;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import com.example.pj_webshop.repositories.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductPropertiesService {

    private final ProductOptionGroupService productOptionGroupService;
    private final ProductOptionService productOptionService;

    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductPropertiesRepository repository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ProductProperties> findAll() {
        return repository.findAll();
    }

    public Optional<ProductProperties> findById(int id) {
        return repository.findById(id);
    }

    public Optional<ProductPropertiesDTO> findWithGroupsById(int id) {
        Optional<ProductProperties> properties = repository.findById(id);
        properties.ifPresent(this::initializeLazyFields);
        return properties.map(this::convertToDto);
    }

    private void initializeLazyFields(@NotNull ProductProperties properties) {
        properties.getProduct().getProductName();
        properties.getProductOptionGroups().forEach(group -> {
            List<ProductOption> options = productOptionRepository.findByGroupId(group.getProductOptionGroupId());
            group.setProductOptions(options);
        });
    }

    private @NotNull ProductPropertiesDTO convertToDto(@NotNull ProductProperties properties) {
        ProductPropertiesDTO dto = new ProductPropertiesDTO();
        dto.setProductPropertiesId(properties.getProductPropertiesId());
        dto.setDescription(properties.getDescription());
        dto.setProduct(convertToDto(properties.getProduct()));
        dto.setProductOptionGroups(properties.getProductOptionGroups().stream()
                .map(this::convertToDto).collect(Collectors.toList()));
        return dto;
    }

    private @NotNull ProductDTO convertToDto(@NotNull Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductImage(product.getProductImage());
        dto.setPrice(product.getPrice());
        dto.setProductDescription(product.getProductDescription());
        return dto;
    }

    private @NotNull ProductOptionGroupDTO convertToDto(@NotNull ProductOptionGroup group) {
        ProductOptionGroupDTO dto = new ProductOptionGroupDTO();
        dto.setProductOptionGroupId(group.getProductOptionGroupId());
        dto.setName(group.getName());
        dto.setAvailableOptionsState(group.getAvailableOptionsState().toString());
        dto.setRequired(group.isRequired());
        dto.setGroupModificationMode(group.getGroupModificationMode().name());
        dto.setProductOptions(group.getProductOptions().stream()
                .map(this::convertToDto).collect(Collectors.toList()));
        return dto;
    }

    private @NotNull ProductOptionDTO convertToDto(@NotNull ProductOption option) {
        ProductOptionDTO dto = new ProductOptionDTO();
        dto.setProductOptionId(option.getProductOptionId());
        dto.setName(option.getName());
        dto.setImage(option.getImage());
        dto.setPrice(option.getPrice());
        dto.setAccessible(option.isAccessible());
        return dto;
    }

    public Optional<ProductProperties> addNew(@NotNull ProductProperties properties) {
        if (repository.existsById(properties.getProductPropertiesId())) {
            return Optional.empty();
        }

        Product product = properties.getProduct();
        if (product != null && !productRepository.existsById(product.getProductId())) {
            product = productRepository.saveAndFlush(product);
            properties.setProduct(product);
        }

        if (properties.getProductOptionGroups() != null) {
            for (ProductOptionGroup group : properties.getProductOptionGroups()) {
                if (group.getProductOptions() != null) {
                    List<ProductOption> options = new ArrayList<>();
                    for (ProductOption option : group.getProductOptions()) {
                        option = productOptionRepository.saveAndFlush(option);
                        options.add(option);
                    }
                    group.setProductOptions(options);
                }
                group = productOptionGroupRepository.saveAndFlush(group);
            }
        }

        ProductProperties mergedProperties = entityManager.merge(properties);
        repository.saveAndFlush(mergedProperties);
        return Optional.of(mergedProperties);
    }


    public Optional<ProductProperties> update(int id, ProductProperties newProperties) {
        return repository.findById(id).map(existingProperties -> {
            existingProperties.setDescription(newProperties.getDescription());
            existingProperties.setProduct(newProperties.getProduct());

            if (existingProperties.getProductOptionGroups() == null) {
                existingProperties.setProductOptionGroups(new ArrayList<>());
            }

            existingProperties.getProductOptionGroups().clear();
            if (newProperties.getProductOptionGroups() != null) {
                existingProperties.getProductOptionGroups().addAll(newProperties.getProductOptionGroups());
            }

            return repository.saveAndFlush(existingProperties);
        });
    }

    public boolean delete(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
