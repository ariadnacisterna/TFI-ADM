package com.example.tfiadm.repository;

import com.example.tfiadm.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByIdfactura(Integer idfactura);
    Optional<Factura> findByVenta_Idventa(Integer ventaId);

}
