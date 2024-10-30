package com.example.tfiadm.repository;

import com.example.tfiadm.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByCUIL(Long CUIL);
    List<Empleado> findAllByBorradoFalse();
    Optional<Empleado> findByCUILAndBorradoFalse(Long CUIL);

}