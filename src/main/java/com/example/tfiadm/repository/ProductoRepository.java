package com.example.tfiadm.repository;

import com.example.tfiadm.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByIdproducto(Integer idproducto);
}
