package com.example.pj_webshop.entities.interim;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "clients_have_orders")
@NoArgsConstructor
@AllArgsConstructor
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientOrderId;

    private int clientId;

    private int orderId;
}
