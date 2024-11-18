package com.example.tfiadm.repository;

import com.example.tfiadm.model.Compra;
import com.example.tfiadm.model.DetalleFactura;
import com.example.tfiadm.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    List<DetalleFactura> findByFactura_Idfactura(Integer factura_idfactura);
}
