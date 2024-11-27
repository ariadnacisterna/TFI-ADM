package com.example.tfiadm.repository;

import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.InicioSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InicioSesionRepository extends JpaRepository<InicioSesion, Long> {
    Optional<InicioSesion> findByEmpleado(Empleado empleado);
}
