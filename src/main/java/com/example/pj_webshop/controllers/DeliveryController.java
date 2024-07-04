package com.example.pj_webshop.controllers;

import com.example.pj_webshop.entities.Delivery;
import com.example.pj_webshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deliveries")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.findAll();
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable int id) {
        Optional<Delivery> deliveryOpt = deliveryService.findById(id);
        return deliveryOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/find/date")
    public ResponseEntity<List<Delivery>> getAllByDeliveryDate(@RequestParam Date deliveryDate) {
        List<Delivery> deliveries = deliveryService.findAllByDeliveryDate(deliveryDate);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/find/address")
    public ResponseEntity<List<Delivery>> getAllByAddress(@RequestParam String address) {
        List<Delivery> deliveries = deliveryService.findAllByAddress(address);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @GetMapping("/find/method")
    public ResponseEntity<List<Delivery>> getAllByDeliveryMethod(@RequestParam String deliveryMethod) {
        List<Delivery> deliveries = deliveryService.findAllByDeliveryMethod(deliveryMethod);
        if (deliveries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Delivery> addNewDelivery(@RequestBody Delivery delivery) {
        Optional<Delivery> deliveryOpt = deliveryService.addNew(delivery);
        return deliveryOpt.map(savedDelivery -> ResponseEntity.status(HttpStatus.OK).body(savedDelivery))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<Delivery> updateDelivery(
            @PathVariable int id,
            @RequestParam String address,
            @RequestParam String deliveryMethod,
            @RequestParam Date deliveryDate) {
        Optional<Delivery> deliveryOpt = deliveryService.updateDeliveryDetails(id, address, deliveryMethod, deliveryDate);
        return deliveryOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeliveryById(@PathVariable int id) {
        boolean isDeleted = deliveryService.deleteById(id);
        if (!isDeleted)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
