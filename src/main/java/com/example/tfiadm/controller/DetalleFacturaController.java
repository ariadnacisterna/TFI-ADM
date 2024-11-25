package com.example.tfiadm.controller;

import com.example.tfiadm.dto.DetalleFacturaRequest;
import com.example.tfiadm.dto.DetalleFacturaResponse;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.exception.ProductoNotFoundException;
import com.example.tfiadm.service.DetalleFacturaService;
import com.example.tfiadm.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/detalle")
@RequiredArgsConstructor
public class DetalleFacturaController {
    private final DetalleFacturaService detalleFacturaService;
    private final FacturaService facturaService;

    @PostMapping
    public DetalleFacturaResponse create(@RequestBody DetalleFacturaRequest request) throws ProductoNotFoundException, FacturaNotFoundException {
        return detalleFacturaService.create(request);
    }

   @GetMapping("/{id}")
    public List<DetalleFacturaResponse> findByFacturaId(@PathVariable("id") Integer id) {
        return detalleFacturaService.findAllByFacturaId(id);
    }
}