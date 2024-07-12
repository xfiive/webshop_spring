package com.example.pj_webshop.entities.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.sql.Date;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryId;

    @Column(nullable = false)
    private String deliveryCategory;

    private Date deliverOnTime;

    private String deliverToAddress;

    private String deliveryRequirements;

//    @OneToOne(mappedBy = "delivery")
//    @JsonBackReference
//    private Order order;
}
