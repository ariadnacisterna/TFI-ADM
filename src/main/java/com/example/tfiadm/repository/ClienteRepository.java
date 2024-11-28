package com.example.tfiadm.repository;

import com.example.tfiadm.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCuil(Long cuil);
    List<Cliente> findAllByBorradoFalse();
    Optional<Cliente> findByCuilAndBorradoFalse(Long cuil);
}
