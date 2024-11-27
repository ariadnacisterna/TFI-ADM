package com.example.tfiadm.repository;

import com.example.tfiadm.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByCuil(Long cuil);
    List<Empleado> findAllByBorradoFalse();
    Optional<Empleado> findByCuilAndBorradoFalse(Long cuil);
    Optional<Empleado> findByIdempleado(Integer idempleado);
    List<Empleado> findByEsGerenteTrue();

<<<<<<< HEAD
    Empleado findByMail(String mail);
=======
    Optional<Empleado> findByMail(String mail);
>>>>>>> 3556b8b09cb73e0c5222ca712261f9e3300fa746
}