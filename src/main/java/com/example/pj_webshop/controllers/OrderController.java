package com.example.pj_webshop.controllers;

import com.example.pj_webshop.entities.Order;
import com.example.pj_webshop.entities.Product;
import com.example.pj_webshop.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Order>> findAllOrders() {
        List<Order> orders = orderService.findAll();
        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/find/{id}/products")
    public ResponseEntity<List<Product>> findProductsByOrderId(@PathVariable int id) {
        List<Product> products = orderService.findAllOrderedProductByOrderId(id);
        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable int id) {
        Optional<Order> orderOpt = orderService.findById(id);
        return orderOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @GetMapping("/find/status/{status}")
    public ResponseEntity<List<Order>> findOrdersByStatus(@PathVariable boolean status) {
        List<Order> orders = orderService.findAllByStatus(status);
        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/client/{clientId}/status/{status}")
    public ResponseEntity<List<Order>> findOrdersByClientIdAndStatus(@PathVariable int clientId, @PathVariable boolean status) {
        List<Order> orders = orderService.findAllByClientIdAndStatus(clientId, status);
        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order) {
        Optional<Order> orderOpt = orderService.addNew(order);
        return orderOpt.map(savedOrder -> ResponseEntity.status(HttpStatus.CREATED).body(savedOrder))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PutMapping("/change/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable int id, @RequestBody Order order) {
        Optional<Order> orderOpt = orderService.updateOrder(id, order);
        return orderOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PutMapping("/change/order/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int id, @RequestParam boolean status) {
        Optional<Order> orderOpt = orderService.updateOrderStatus(id, status);
        return orderOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable int id) {
        boolean isDeleted = orderService.deleteById(id);
        if (!isDeleted)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
