package com.example.pj_webshop.controllers.products;

import com.example.pj_webshop.entities.models.dto.ProductPropertiesDTO;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.services.products.ProductPropertiesService;
import com.example.pj_webshop.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/properties")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class ProductPropertiesController {

    private final ProductPropertiesService service;
    private final ProductService productService;

    @GetMapping("/find/all")
    public ResponseEntity<List<ProductProperties>> getAll() {
        List<ProductProperties> properties = service.findAll();
        if (properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductPropertiesDTO> getById(@PathVariable int id) {
        Optional<ProductPropertiesDTO> properties = service.findWithGroupsById(id);
        return properties.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductProperties> add(@RequestBody ProductProperties properties) {
        Optional<ProductProperties> savedProperties = service.addNew(properties);
        return savedProperties.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProductProperties> update(@PathVariable int id, @RequestBody ProductProperties properties) {
        Optional<ProductProperties> updatedProperties = service.update(id, properties);
        return updatedProperties.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean isDeleted = service.delete(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
