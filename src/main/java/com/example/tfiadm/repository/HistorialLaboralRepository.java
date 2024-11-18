package com.example.tfiadm.repository;

import com.example.tfiadm.model.HistorialLaboral;
import com.example.tfiadm.model.Interaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialLaboralRepository extends JpaRepository<HistorialLaboral, Long> {
    Optional<HistorialLaboral> findByIdhistoriallaboral(Integer idhistoriallaboral);
    List<HistorialLaboral> findByEmpleado_Idempleado(Integer empleadoId);
}
