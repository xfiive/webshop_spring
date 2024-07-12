package com.example.pj_webshop.repositories.interim;

import com.example.pj_webshop.entities.interim.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
    List<ClientOrder> findByClientId(int clientId);

    List<ClientOrder> findByOrderId(int orderId);
}
