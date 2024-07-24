package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.products.ProductOptionGroup;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.repositories.products.ProductOptionGroupRepository;
import com.example.pj_webshop.repositories.products.ProductOptionRepository;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductOptionGroupService {

    private final ProductOptionGroupRepository repository;
    private final ProductOptionRepository optionRepository;
    private final ProductPropertiesRepository propertiesRepository;

    public List<ProductOptionGroup> findAll() {
        return repository.findAll();
    }

    public Optional<ProductOptionGroup> findById(int id) {
        return repository.findById(id);
    }

    public List<ProductOptionGroup> findByProductPropertiesId(int productPropertiesId) {
        return repository.findByProductPropertiesId(productPropertiesId);
    }

    @Transactional
    public Optional<ProductOptionGroup> addNew(@NotNull ProductOptionGroup optionGroup) {
        if (optionGroup.getProductOptions() != null) {
            optionGroup.setProductOptions(optionGroup.getProductOptions().stream()
                    .map(option -> optionRepository.findById(option.getProductOptionId()).orElseThrow(() -> new IllegalArgumentException("ProductOption not found: " + option.getProductOptionId())))
                    .collect(Collectors.toList()));
        }

        return Optional.of(repository.save(optionGroup));
    }

    public Optional<ProductOptionGroup> update(int id, ProductOptionGroup optionGroup) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        optionGroup.setProductOptionGroupId(id);
        repository.save(optionGroup);
        return Optional.of(optionGroup);
    }

    public boolean delete(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<ProductOptionGroup> updatePropertiesId(int id, int newPropertiesId) {
        Optional<ProductOptionGroup> optionalProductOptionGroup = repository.findById(id);

        if (optionalProductOptionGroup.isEmpty())
            return Optional.empty();

        ProductOptionGroup productOptionGroup = optionalProductOptionGroup.get();
        Optional<ProductProperties> newProperties = propertiesRepository.findById(newPropertiesId);

        if (newProperties.isEmpty())
            return Optional.empty();

        productOptionGroup.setProductPropertiesId(newProperties.get().getProductPropertiesId());
        return Optional.of(repository.saveAndFlush(productOptionGroup));
    }
}
