package com.example.tfiadm.repository;

import com.example.tfiadm.model.Cliente;
import com.example.tfiadm.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByCliente_idcliente(Integer id);

    List<Venta> findByEmpleado_idempleado(Integer id);

    Optional<Venta> findByIdventa(Integer id);
}