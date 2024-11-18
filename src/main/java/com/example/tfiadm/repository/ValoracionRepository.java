package com.example.tfiadm.repository;

import com.example.tfiadm.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    Optional<Valoracion> findByCompra_Idcompra(Integer compra_idcompra);
    List<Valoracion> findByCompra_IdcompraIn(List<Integer> compraIds);
}
