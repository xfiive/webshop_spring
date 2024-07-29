package com.example.pj_webshop.controllers.products;

import com.example.pj_webshop.entities.models.dto.ProductPropertiesDTO;
import com.example.pj_webshop.entities.models.products.ProductProperties;
import com.example.pj_webshop.services.products.ProductPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products/properties")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class ProductPropertiesController {

    private final ProductPropertiesService service;

    @GetMapping("/find/all")
    public ResponseEntity<List<ProductPropertiesDTO>> getAll() {
        List<ProductProperties> properties = service.findAll();
        List<ProductPropertiesDTO> propertiesDTO = properties.stream()
                .map(service::convertToDto)
                .collect(Collectors.toList());
        if (propertiesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(propertiesDTO, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductPropertiesDTO> getById(@PathVariable int id) {
        Optional<ProductPropertiesDTO> properties = service.findWithGroupsById(id);
        return properties.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductPropertiesDTO> add(@RequestBody ProductPropertiesDTO propertiesDTO) {
        Optional<ProductPropertiesDTO> savedProperties = service.addNew(propertiesDTO);
        return savedProperties.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProductPropertiesDTO> update(@PathVariable int id, @RequestBody ProductPropertiesDTO propertiesDTO) {
        Optional<ProductPropertiesDTO> updatedProperties = service.update(id, propertiesDTO);
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
