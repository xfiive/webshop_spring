package com.example.pj_webshop.controllers.products;

import com.example.pj_webshop.entities.models.products.ProductOption;
import com.example.pj_webshop.services.products.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products/option")
public class ProductOptionController {

    private final ProductOptionService service;

    @Autowired
    public ProductOptionController(ProductOptionService service) {
        this.service = service;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<ProductOption>> getAll() {
        List<ProductOption> options = service.findAll();
        if (options.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductOption> getById(@PathVariable int id) {
        Optional<ProductOption> option = service.findById(id);
        return option.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    /*
        Когда создаёшь сущность, то делай примерно как тут.
        Оно поставит groupId в 0, затем либо поменяешь, либо присвоишь, иначе не получится вытащить все опции из группы опций.
        {
            "name": "Memory option for a Laptop",
            "groupId": "",
            "image": "BBB",
            "price": 160.00,
            "accessible": true
        }
     */

    @PostMapping("/add")
    public ResponseEntity<ProductOption> add(@RequestBody ProductOption option) {
        Optional<ProductOption> savedOption = service.addNew(option);
        return savedOption.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProductOption> update(@PathVariable int id, @RequestBody ProductOption option) {
        Optional<ProductOption> updatedOption = service.update(id, option);
        return updatedOption.map(ResponseEntity::ok)
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
