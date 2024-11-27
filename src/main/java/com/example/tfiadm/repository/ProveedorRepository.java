package com.example.tfiadm.repository;

import com.example.tfiadm.model.Cliente;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p FROM Proveedor p WHERE p.contrato_fin <= :fechaLimite AND p.borrado = false")
    List<Proveedor> findProveedoresContratoCercaExpirarAndBorradoFalse(@Param("fechaLimite") LocalDate fechaLimite);

    Optional<Proveedor> findByCuil(Long cuil);

    List<Proveedor> findAllByBorradoFalse();

    Optional<Proveedor> findByCuilAndBorradoFalse(Long cuil);

    Optional<Proveedor> findByIdproveedor(Integer id);
}
