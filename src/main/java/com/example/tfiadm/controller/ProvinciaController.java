package com.example.tfiadm.controller;

import com.example.tfiadm.dto.ProvinciaResponse;
import com.example.tfiadm.service.ProvinciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/adm/provincias")
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    @GetMapping("/por-pais/{paisId}")
    public ResponseEntity<List<ProvinciaResponse>> obtenerProvinciasPorPais(@PathVariable Integer paisId) {
        List<ProvinciaResponse> provincias = provinciaService.obtenerProvinciasPorPais(paisId);
        return ResponseEntity.ok(provincias);
    }
}
