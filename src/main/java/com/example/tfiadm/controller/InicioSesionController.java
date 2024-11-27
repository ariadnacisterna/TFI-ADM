package com.example.tfiadm.controller;

import com.example.tfiadm.dto.InicioSesionRequest;
import com.example.tfiadm.model.InicioSesion;
import com.example.tfiadm.service.InicioSesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/adm/login")
@RequiredArgsConstructor

public class InicioSesionController {
    @Autowired
    private InicioSesionService inicioSesionService;

    @PostMapping()
    public ResponseEntity<String> login(@RequestBody InicioSesionRequest request){
        try{
            String mensaje = inicioSesionService.iniciarSesion(request);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
