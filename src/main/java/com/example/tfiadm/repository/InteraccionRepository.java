package com.example.tfiadm.repository;

import com.example.tfiadm.model.Interaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Long> {
    List<Interaccion> findByCliente_Cuil(Long cuilCliente);
}