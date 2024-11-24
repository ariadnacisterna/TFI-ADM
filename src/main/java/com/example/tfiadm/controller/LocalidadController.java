package com.example.tfiadm.controller;

import com.example.tfiadm.dto.LocalidadResponse;
import com.example.tfiadm.service.LocalidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/adm/localidades")
public class LocalidadController {

    private final LocalidadService localidadService;

    @GetMapping("/por-provincia/{provinciaId}")
    public ResponseEntity<List<LocalidadResponse>> obtenerLocalidadesPorProvincia(@PathVariable Integer provinciaId) {
        List<LocalidadResponse> localidades = localidadService.obtenerLocalidadesPorProvincia(provinciaId);
        return ResponseEntity.ok(localidades);
    }
}
