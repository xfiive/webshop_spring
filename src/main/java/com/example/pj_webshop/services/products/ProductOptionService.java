package com.example.pj_webshop.services.products;

import com.example.pj_webshop.entities.models.products.ProductOption;
import com.example.pj_webshop.repositories.products.ProductOptionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    @Autowired
    public ProductOptionService(ProductOptionRepository productOptionRepository) {
        this.productOptionRepository = productOptionRepository;
    }

    public List<ProductOption> findAll() {
        return productOptionRepository.findAll();
    }

    public Optional<ProductOption> findById(int id) {
        return productOptionRepository.findById(id);
    }

    /*
        TODO::
          В чём сейчас проблема: когда я получаю группу, то не получаю все её опции. Если убрать @JsonIgnore, то тогда ошибка ленивой инициализации.
          Нужно добавить метод changeGroupId в сервисе ProductOptionService(и в контроллер соответственно).
          Протестировать.
          Затем поменять на уже существующих.
          Затем , возможно, добавить метод на добавление/удаление опции у группы.
          Затем допилить и протестировать ProductOptionService::findByGroupId.
          То же самое для Properties, но не факт.
        TODO
     */

    public List<ProductOption> findByGroupId(int groupId) {
        return productOptionRepository.findByGroupId(groupId);
    }

    public Optional<ProductOption> addNew(@NotNull ProductOption option) {
        if (productOptionRepository.existsById(option.getProductOptionId())) {
            return Optional.empty();
        }
        productOptionRepository.save(option);
        return Optional.of(option);
    }

    public Optional<ProductOption> update(int id, ProductOption option) {
        if (!productOptionRepository.existsById(id)) {
            return Optional.empty();
        }
        option.setProductOptionId(id);
        productOptionRepository.save(option);
        return Optional.of(option);
    }

    public boolean delete(int id) {
        if (!productOptionRepository.existsById(id)) {
            return false;
        }
        productOptionRepository.deleteById(id);
        return true;
    }
}
