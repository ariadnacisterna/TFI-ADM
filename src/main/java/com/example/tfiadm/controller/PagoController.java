package com.example.tfiadm.controller;

import com.example.tfiadm.dto.PagoRequest;
import com.example.tfiadm.dto.PagoResponse;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/adm/pagos")
@RequiredArgsConstructor

public class PagoController {
    private final PagoService pagoService;

    @PostMapping()
    public PagoResponse create(@RequestBody PagoRequest request) throws FacturaNotFoundException, ErrorSintaxisException, IOException {
        return pagoService.create(request);
    }

    @GetMapping("/{id}")
    public PagoResponse findByFacturaId(@PathVariable("id") Integer id) {
        return pagoService.findByIdFactura(id);
    }


}
