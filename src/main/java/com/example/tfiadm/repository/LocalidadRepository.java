package com.example.tfiadm.repository;

import com.example.tfiadm.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
    List<Localidad> findByProvinciaIdprovincia(Integer provinciaId);
}
