package com.example.tfiadm.repository;

import com.example.tfiadm.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByFactura_Idfactura(Integer idfactura);
}
