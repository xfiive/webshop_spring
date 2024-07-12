package com.example.pj_webshop.services;

import com.example.pj_webshop.entities.models.Delivery;
import com.example.pj_webshop.repositories.DeliveryRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> findAll() {
        return this.deliveryRepository.findAll();
    }

    public Optional<Delivery> findById(int id) {
        return this.deliveryRepository.findById(id);
    }

    public List<Delivery> findAllByDeliveryDate(Date deliveryDate) {
        return this.deliveryRepository.findAllByDeliverOnTime(deliveryDate);
    }

    public List<Delivery> findAllByAddress(String address) {
        return this.deliveryRepository.findAllByDeliverToAddress(address);
    }

    public List<Delivery> findAllByDeliveryMethod(String deliveryMethod) {
        return this.deliveryRepository.findAllByDeliveryCategory(deliveryMethod);
    }

    public Optional<Delivery> addNew(@NotNull Delivery delivery) {
        var existingDelivery = this.findById(delivery.getDeliveryId());
        if (existingDelivery.isEmpty()) {
            this.deliveryRepository.save(delivery);
            return Optional.of(delivery);
        }
        return Optional.empty();
    }

    public Optional<Delivery> updateDeliveryDetails(int id, String address, String deliveryMethod, Date deliveryDate) {
        Optional<Delivery> existingDeliveryOpt = deliveryRepository.findById(id);

        if (existingDeliveryOpt.isEmpty())
            return Optional.empty();

        Delivery existingDelivery = existingDeliveryOpt.get();
        existingDelivery.setDeliverToAddress(address);
        existingDelivery.setDeliveryCategory(deliveryMethod);
        existingDelivery.setDeliverOnTime(deliveryDate);

        Delivery savedDelivery = deliveryRepository.save(existingDelivery);

        return Optional.of(savedDelivery);
    }


    public boolean deleteById(int id) {
        Optional<Delivery> delivery = this.deliveryRepository.findById(id);

        if (delivery.isEmpty())
            return false;

        this.deliveryRepository.deleteById(id);
        return true;
    }
}
