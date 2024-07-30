package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.dto.ProductDTO;
import com.example.pj_webshop.entities.models.dto.ProductOptionDTO;
import com.example.pj_webshop.entities.models.dto.ProductOptionGroupDTO;
import com.example.pj_webshop.entities.models.dto.ProductPropertiesDTO;
import com.example.pj_webshop.entities.models.products.*;
import com.example.pj_webshop.repositories.products.ProductOptionGroupRepository;
import com.example.pj_webshop.repositories.products.ProductOptionRepository;
import com.example.pj_webshop.repositories.products.ProductPropertiesRepository;
import com.example.pj_webshop.repositories.products.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(ProductPropertiesService.class);
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductPropertiesRepository repository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

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

    @NotNull
    public ProductPropertiesDTO convertToDto(@NotNull ProductProperties properties) {
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
        dto.setProductPropertiesId(product.getProductPropertiesId());
        dto.setProductDescription(product.getProductDescription());
        return dto;
    }

    private @NotNull ProductOptionGroupDTO convertToDto(@NotNull ProductOptionGroup group) {
        ProductOptionGroupDTO dto = new ProductOptionGroupDTO();
        dto.setProductOptionGroupId(group.getProductOptionGroupId());
        dto.setName(group.getName());
        if (group.getAvailableOptionsState() != null)
            dto.setAvailableOptionsState(group.getAvailableOptionsState().toString());
        else
            dto.setAvailableOptionsState(AvailableOptionsState.UNKNOWN.toString());
        dto.setRequired(group.isRequired());
        dto.setGroupModificationMode(group.getGroupModificationMode().name());
        dto.setProductPropertiesId(group.getProductPropertiesId());
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
        dto.setGroupId(option.getGroupId());
        dto.setAccessible(option.isAccessible());
        return dto;
    }

    private @NotNull ProductProperties convertToEntity(@NotNull ProductPropertiesDTO dto) {
        ProductProperties entity = new ProductProperties();
        entity.setProductPropertiesId(dto.getProductPropertiesId());
        entity.setDescription(dto.getDescription());
        entity.setProduct(convertToEntity(dto.getProduct()));
        entity.setProductOptionGroups(dto.getProductOptionGroups().stream()
                .map(this::convertToEntity).collect(Collectors.toList()));
        return entity;
    }

    private @NotNull Product convertToEntity(@NotNull ProductDTO dto) {
        Product entity = new Product();
        entity.setProductId(dto.getProductId());
        entity.setProductName(dto.getProductName());
        entity.setProductDescription(dto.getProductDescription());
        entity.setPrice(dto.getPrice());
        entity.setProductPropertiesId(dto.getProductPropertiesId());
        entity.setProductImage(dto.getProductImage());
        return entity;
    }

    private @NotNull ProductOptionGroup convertToEntity(@NotNull ProductOptionGroupDTO dto) {
        ProductOptionGroup entity = new ProductOptionGroup();
        entity.setProductOptionGroupId(dto.getProductOptionGroupId());
        entity.setName(dto.getName());
        entity.setRequired(dto.isRequired());
        entity.setProductPropertiesId(dto.getProductPropertiesId());
        entity.setGroupModificationMode(GroupModificationMode.fromCommand(dto.getGroupModificationMode()));
        entity.setAvailableOptionsState(AvailableOptionsState.valueOf(dto.getAvailableOptionsState()));
        entity.setProductOptions(dto.getProductOptions().stream()
                .map(this::convertToEntity).collect(Collectors.toList()));
        return entity;
    }

    private @NotNull ProductOption convertToEntity(@NotNull ProductOptionDTO dto) {
        ProductOption entity = new ProductOption();
        entity.setProductOptionId(dto.getProductOptionId());
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setPrice(dto.getPrice());
        entity.setGroupId(dto.getGroupId());
        entity.setAccessible(dto.isAccessible());
        return entity;
    }

    public Optional<ProductPropertiesDTO> addNew(@NotNull ProductPropertiesDTO propertiesDTO) {
        ProductProperties properties = convertToEntity(propertiesDTO);

        // Если id продукта присутствует и не равен 0, проверяем, существует ли такой продукт
        if (properties.getProduct().getProductId() != 0 && productRepository.existsById(properties.getProduct().getProductId())) {
            // Обновляем существующий продукт
            Product existingProduct = productRepository.findById(properties.getProduct().getProductId()).orElse(null);
            if (existingProduct != null) {
                existingProduct.setProductName(properties.getProduct().getProductName());
                existingProduct.setProductDescription(properties.getProduct().getProductDescription());
                existingProduct.setPrice(properties.getProduct().getPrice());
                existingProduct.setProductImage(properties.getProduct().getProductImage());
                productRepository.saveAndFlush(existingProduct);
                properties.setProduct(existingProduct);
            }
        } else {
            // Создаем новый продукт
            properties.getProduct().setProductId(0);
        }

        // Если id ProductProperties присутствует и не равен 0, проверяем, существует ли такой ProductProperties
        if (properties.getProductPropertiesId() != 0 && repository.existsById(properties.getProductPropertiesId())) {
            // Обновляем существующий ProductProperties
            ProductProperties existingProperties = repository.findById(properties.getProductPropertiesId()).orElse(null);
            if (existingProperties != null) {
                existingProperties.setDescription(properties.getDescription());
                existingProperties.setProduct(properties.getProduct());
                properties = existingProperties;
            }
        } else {
            // Создаем новый ProductProperties
            properties.setProductPropertiesId(0);
        }

        // Устанавливаем id групп и опций в 0 для новых записей
        if (properties.getProductOptionGroups() != null) {
            for (ProductOptionGroup group : properties.getProductOptionGroups()) {
                if (group.getProductOptionGroupId() == 0) {
                    group.setProductPropertiesId(0);
                    for (ProductOption option : group.getProductOptions()) {
                        option.setProductOptionId(0);
                    }
                }
            }
        }

        // Сохраняем основную сущность
        ProductProperties savedProperties = repository.saveAndFlush(properties);

        // Обновляем связи с ID, сгенерированными базой данных
        if (savedProperties.getProduct() != null) {
            savedProperties.getProduct().setProductPropertiesId(savedProperties.getProductPropertiesId());
            productRepository.saveAndFlush(savedProperties.getProduct());
        }

        if (savedProperties.getProductOptionGroups() != null) {
            for (ProductOptionGroup group : savedProperties.getProductOptionGroups()) {
                group.setProductPropertiesId(savedProperties.getProductPropertiesId());
                ProductOptionGroup savedGroup = productOptionGroupRepository.saveAndFlush(group);
                if (group.getProductOptions() != null) {
                    for (ProductOption option : group.getProductOptions()) {
                        option.setGroupId(savedGroup.getProductOptionGroupId());
                        productOptionRepository.saveAndFlush(option);
                    }
                }
            }
        }

        return Optional.of(convertToDto(savedProperties));
    }

    public Optional<ProductPropertiesDTO> update(int id, ProductPropertiesDTO newPropertiesDTO) {
        return repository.findById(id).map(existingProperties -> {
            existingProperties.setDescription(newPropertiesDTO.getDescription());
            existingProperties.setProduct(convertToEntity(newPropertiesDTO.getProduct()));

            if (existingProperties.getProductOptionGroups() == null) {
                existingProperties.setProductOptionGroups(new ArrayList<>());
            }

            existingProperties.getProductOptionGroups().clear();
            if (newPropertiesDTO.getProductOptionGroups() != null) {
                existingProperties.getProductOptionGroups().addAll(newPropertiesDTO.getProductOptionGroups().stream()
                        .map(this::convertToEntity).toList());
            }

            return convertToDto(repository.saveAndFlush(existingProperties));
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
