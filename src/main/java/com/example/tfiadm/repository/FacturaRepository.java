package com.example.tfiadm.repository;

import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Valoracion;
import com.example.tfiadm.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByIdfactura(Integer idfactura);
    Optional<Factura> findByVenta_Idventa(Integer venta_idventa);
}
