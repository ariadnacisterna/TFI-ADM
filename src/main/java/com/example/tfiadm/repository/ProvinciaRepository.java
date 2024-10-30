package com.example.tfiadm.repository;

import com.example.tfiadm.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    Optional<Provincia> findById(Integer id);

}
