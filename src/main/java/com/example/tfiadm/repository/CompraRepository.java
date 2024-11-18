package com.example.tfiadm.repository;

import com.example.tfiadm.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByProveedor_Idproveedor(Integer proveedor_idproveedor);
    Optional<Compra> findByIdcompra(Integer idcompra);
}
