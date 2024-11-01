package com.example.tfiadm.repository;

import com.example.tfiadm.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCuil(Long cuil);
}
