package com.example.tfiadm.controller;

import com.example.tfiadm.dto.VentaRequest;
import com.example.tfiadm.dto.VentaResponse;
import com.example.tfiadm.exception.ClienteNotFoundException;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.VentaNotFoundException;
import com.example.tfiadm.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adm/ventas")
@RequiredArgsConstructor


public class VentaController {
    private final VentaService ventaService;

    @PostMapping()
    public VentaResponse create(@RequestBody VentaRequest request) throws EmpleadoNotFoundException, ClienteNotFoundException, ErrorSintaxisException {
        return ventaService.create(request);
    }
    @GetMapping("empleados/{CUIL}")
    public List<VentaResponse> getByEmpleadoCUIL(@PathVariable("CUIL")Long CUIL) throws EmpleadoNotFoundException {
        return ventaService.findAllByEmpleadoCUIL(CUIL);
    }
    @GetMapping("clientes/{CUIL}")
    public List<VentaResponse> getByClienteCUIL(@PathVariable("CUIL")Long CUIL) throws ClienteNotFoundException {
        return ventaService.findAllByClienteCUIL(CUIL);
    }

    @GetMapping("/{id}")
    public VentaResponse get(@PathVariable Integer id) throws VentaNotFoundException {
        return ventaService.findById(id);
    }

}
