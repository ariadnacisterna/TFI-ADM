package com.example.tfiadm.repository;

import com.example.tfiadm.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

    // Método para obtener provincias por id del país
    List<Provincia> findByPaisIdpais(Integer paisId);
}
