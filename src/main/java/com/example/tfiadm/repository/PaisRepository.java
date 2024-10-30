package com.example.tfiadm.repository;

import com.example.tfiadm.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
    Optional<Pais> findById(Integer id);
}
