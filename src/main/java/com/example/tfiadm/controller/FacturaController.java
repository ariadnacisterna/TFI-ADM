package com.example.tfiadm.controller;

import com.example.tfiadm.dto.FacturaRequest;
import com.example.tfiadm.dto.FacturaResponse;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.exception.VentaNotFoundException;
import com.example.tfiadm.model.Factura;
import com.example.tfiadm.service.EmpleadoService;
import com.example.tfiadm.service.FacturaService;
import com.itextpdf.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adm/facturas")
@RequiredArgsConstructor

public class FacturaController {
    private final FacturaService facturaService;
    private final EmpleadoService empleadoService;

    @PostMapping()
    public FacturaResponse create(@RequestBody FacturaRequest request) throws ErrorSintaxisException, VentaNotFoundException, IOException, java.io.IOException {
        return facturaService.create(request);
    }


    @GetMapping("/{id}")
    public FacturaResponse get(@PathVariable("id") Integer id) throws FacturaNotFoundException {
        return facturaService.findByVentaId(id);
    }

    @PutMapping("/pago/{id}")
    public FacturaResponse update(@PathVariable Integer id) throws FacturaNotFoundException {
        return facturaService.updatePago(id);
    }

}
