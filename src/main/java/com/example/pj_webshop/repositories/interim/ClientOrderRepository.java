package com.example.pj_webshop.repositories.interim;

import com.example.pj_webshop.entities.interim.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
    List<ClientOrder> findByClientId(int clientId);

    List<ClientOrder> findAllByOrderId(int orderId);

    Optional<ClientOrder> findByOrderId(int orderId);
}
