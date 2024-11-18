package com.example.tfiadm.controller;

import com.example.tfiadm.dto.*;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.ProveedorNotFoundException;
import com.example.tfiadm.service.CompraService;
import com.example.tfiadm.service.InteraccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/compra")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping()
    public CompraResponse create(@RequestBody CompraRequest request) throws ProveedorNotFoundException, ErrorSintaxisException {
        return compraService.create(request);
    }

    @GetMapping("/{CUIL}")
    public List<CompraResponse> findAllByProveedorCUIL(@PathVariable Long CUIL) throws ProveedorNotFoundException {
        return compraService.findAllByProveedorCUIL(CUIL);
    }

}
