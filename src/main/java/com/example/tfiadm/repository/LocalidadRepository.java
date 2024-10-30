package com.example.tfiadm.repository;

import com.example.tfiadm.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
    Optional<Localidad> findById(Integer id);
}
