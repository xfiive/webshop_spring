package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.*;
import com.example.pj_webshop.repositories.ClientOrderRepository;
import com.example.pj_webshop.repositories.DeliveryRepository;
import com.example.pj_webshop.repositories.OrderRepository;
import com.example.pj_webshop.repositories.OrderedProductRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private DeliveryRepository deliveryRepository;
    private ClientOrderRepository clientOrderRepository;
    private OrderedProductRepository orderedProductRepository;

    @Autowired
    public void setOrderedProductRepository(OrderedProductRepository orderedProductRepository) {
        this.orderedProductRepository = orderedProductRepository;
    }

    @Autowired
    public void setClientOrderRepository(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllByStatus(boolean status) {
        return orderRepository.findAllByIsDone(status);
    }

    public List<Order> findAllByClientIdAndStatus(int clientId, boolean status) {
        List<ClientOrder> clientOrders = clientOrderRepository.findByClientId(clientId);
        List<Integer> orderIds = clientOrders.stream()
                .map(ClientOrder::getOrderId)
                .collect(Collectors.toList());

        return orderRepository.findAllById(orderIds).stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    public List<Product> findAllOrderedProductByOrderId(int id) {
        var order = orderRepository.findById(id);

        if (order.isEmpty()) {
            return List.of();
        }

        List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrder_OrderId(id);

        return orderedProducts.stream()
                .map(OrderedProduct::getProduct)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Order> addNew(@NotNull Order order) {
        Order savedOrder = orderRepository.save(order);
        if (order.getDelivery() != null) {
            Delivery delivery = order.getDelivery();
            deliveryRepository.save(delivery);

            savedOrder.setDelivery(delivery);
            orderRepository.save(savedOrder);
        }
        return Optional.of(savedOrder);
    }

    public Optional<Order> updateOrder(int id, @NotNull Order order) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);

        if (existingOrderOpt.isEmpty()) {
            return Optional.empty();
        }

        Order existingOrder = existingOrderOpt.get();

        existingOrder.setOrderId(order.getOrderId());
        existingOrder.setOrderPrice(order.getOrderPrice());
        existingOrder.setDone(order.isDone());
        existingOrder.setDelivery(order.getDelivery());
        existingOrder.setCreatedAt(order.getCreatedAt());

        orderRepository.save(existingOrder);

        return Optional.of(existingOrder);
    }

    public Optional<Order> updateOrderStatus(int id, boolean status) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);

        if (existingOrderOpt.isEmpty()) {
            return Optional.empty();
        }

        Order existingOrder = existingOrderOpt.get();

        existingOrder.setDone(status);

        orderRepository.save(existingOrder);

        return Optional.of(existingOrder);
    }

    public boolean deleteById(int id) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);

        if (existingOrderOpt.isEmpty()) {
            return false;
        }

        orderRepository.deleteById(id);

        return true;
    }
}
