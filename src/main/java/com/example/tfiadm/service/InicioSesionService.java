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

    public String iniciarSesion(InicioSesionRequest request) {
        InicioSesion inicioSesion = inicioSesionRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario y/o contraseña incorrectos."));

        if (inicioSesion.getPassword().equals(request.getPassword())) {
            return "Inicio de sesión correcto";
        } else {
            throw new RuntimeException("Usuario y/o contraseña incorrectos.");
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

