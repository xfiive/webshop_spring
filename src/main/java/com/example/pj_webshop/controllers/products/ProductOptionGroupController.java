package com.example.pj_webshop.controllers.products;

import com.example.pj_webshop.entities.models.products.ProductOption;
import com.example.pj_webshop.entities.models.products.ProductOptionGroup;
import com.example.pj_webshop.services.products.ProductOptionGroupService;
import com.example.pj_webshop.services.products.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/group")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductOptionGroupController {

    private final ProductOptionGroupService productOptionGroupService;
    private final ProductOptionService productOptionService;

    @GetMapping("/find/all")
    public ResponseEntity<List<ProductOptionGroup>> getAll() {
        List<ProductOptionGroup> optionGroups = productOptionGroupService.findAll();
        if (optionGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(optionGroups, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductOptionGroup> getById(@PathVariable int id) {
        Optional<ProductOptionGroup> optionGroup = productOptionGroupService.findById(id);
        return optionGroup.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    /*
        TODO::
            Напоминаю:
                Сущность Option НЕ может принадлежать более чем одной группе. Думаю, понятно, почему, но чисто на всякий.
        TODO
    */
    @GetMapping("/find/{groupId}/options")
    public ResponseEntity<List<ProductOption>> getOptionsByGroupId(@PathVariable int groupId) {
        List<ProductOption> options = productOptionService.findByGroupId(groupId);
        if (options.isEmpty())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductOptionGroup> add(@RequestBody ProductOptionGroup optionGroup) {
        Optional<ProductOptionGroup> savedOptionGroup = productOptionGroupService.addNew(optionGroup);
        return savedOptionGroup.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/change/{id}/propertiesId")
    public ResponseEntity<ProductOptionGroup> updatePropertiesId(@PathVariable int id, @RequestParam int propertiesId) {
        Optional<ProductOptionGroup> optionGroup = productOptionGroupService.updatePropertiesId(id, propertiesId);
        return optionGroup.map(productOptionGroup -> new ResponseEntity<>(productOptionGroup, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProductOptionGroup> update(@PathVariable int id, @RequestBody ProductOptionGroup optionGroup) {
        Optional<ProductOptionGroup> updatedOptionGroup = productOptionGroupService.update(id, optionGroup);
        return updatedOptionGroup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean isDeleted = productOptionGroupService.delete(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
