package com.example.pj_webshop.entities.authentication;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "administration")
@Data
public class Administration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @Column(nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

}
