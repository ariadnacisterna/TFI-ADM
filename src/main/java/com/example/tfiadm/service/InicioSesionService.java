package com.example.tfiadm.service;

import com.example.tfiadm.dto.EmpleadoResponse;
import com.example.tfiadm.dto.InicioSesionRequest;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.InicioSesion;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.example.tfiadm.repository.InicioSesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InicioSesionService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private InicioSesionRepository inicioSesionRepository;

    public InicioSesion iniciarSesion(InicioSesionRequest request) {
        Empleado empleado = empleadoRepository.findByMail(request.getUsername());

        if (empleado != null && empleado.getCuil().toString().equals(request.getPassword())) {
            InicioSesion inicioSesion = new InicioSesion();
            inicioSesion.setId(Long.valueOf(empleado.getIdempleado()));
            inicioSesion.setUsername(request.getUsername());
            inicioSesion.setPassword(request.getPassword());
            inicioSesion.setEmpleado(empleado);

            EmpleadoResponse empleadoResponseDTO = new EmpleadoResponse(empleado);
            return inicioSesionRepository.save(inicioSesion);
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    @Transactional
    public InicioSesion createInicioSesion(Empleado empleado) {

        InicioSesion existingInicioSesion = inicioSesionRepository.findByEmpleado(empleado)
                .orElse(null);

        if (existingInicioSesion != null) {
            return existingInicioSesion;
        }

        InicioSesion inicioSesion = InicioSesion.builder()
                .empleado(empleado)
                .username(empleado.getMail())
                .password(String.valueOf(empleado.getCuil()))
                .build();

        empleado.setInicioSesion(inicioSesion);

        InicioSesion savedInicioSesion = inicioSesionRepository.save(inicioSesion);

        return savedInicioSesion;
    }

    @Transactional
    public InicioSesion updateInicioSesion(Empleado empleado, String newMail) {
        InicioSesion inicioSesion = inicioSesionRepository.findByEmpleado(empleado)
                .orElseThrow(() -> new RuntimeException("Inicio de sesión no encontrado"));

        inicioSesion.setUsername(newMail);

        return inicioSesionRepository.save(inicioSesion);
    }
}

