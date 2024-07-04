package com.example.pj_webshop.repositories;

import com.example.pj_webshop.entities.Administration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Administration, Integer> {
    Optional<Administration> findByUsername(String username);
}
