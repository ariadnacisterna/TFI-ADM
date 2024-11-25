package com.example.tfiadm.repository;

import com.example.tfiadm.model.Venta;
import com.example.tfiadm.model.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    List<DetalleFactura> findByFactura_Idfactura(Integer factura_idfactura);

}
