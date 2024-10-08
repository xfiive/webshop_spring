package com.example.pj_webshop.repositories;

import com.example.pj_webshop.entities.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    List<Delivery> findAllByDeliverOnTime(Date deliveryDate);

    List<Delivery> findAllByDeliverToAddress(String address);

    List<Delivery> findAllByDeliveryCategory(String deliveryMethod);
}
