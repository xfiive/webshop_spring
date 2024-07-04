package com.example.pj_webshop.repositories;

import com.example.pj_webshop.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findByPhone(String phone);
}
