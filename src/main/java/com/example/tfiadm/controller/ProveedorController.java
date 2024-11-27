package com.example.tfiadm.controller;

import com.example.tfiadm.dto.*;
import com.example.tfiadm.exception.*;
import com.example.tfiadm.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/proveedor")
@RequiredArgsConstructor

public class ProveedorController {
    private final ProveedorService proveedorService;

    @PostMapping()
    public ProveedorResponse create(@RequestBody ProveedorRequest request) throws CUILAlreadyInUseException, LocalidadNotFoundException, ErrorSintaxisException {
        return proveedorService.create(request);
    }

    @GetMapping()
    public List<ProveedorResponse> getAll() {return proveedorService.getAll();}

    @GetMapping("/{CUIL}")
    public ProveedorResponse getProveedorByCUIL (@PathVariable("CUIL") Long CUIL) throws ProveedorNotFoundException {
        return proveedorService.getProveedorByCUIL(CUIL);
    }

    @GetMapping("/inicio")
    public List<ProveedorResponse> getProveedoresContratoPorExpirarAndBorradoFalse() {
        return proveedorService.getProveedoresContratoPorExpirarAndBorradoFalse();
    }

    @PutMapping("/{CUIL}")
    public ProveedorResponse update(@PathVariable Long CUIL, @RequestBody ProveedorRequest request) throws ProveedorNotFoundException, LocalidadNotFoundException, ErrorSintaxisException {
        return proveedorService.updateProveedor(CUIL, request);
    }

    @DeleteMapping("/{CUIL}")
    public ProveedorResponse deleteProveedor(@PathVariable Long CUIL) throws ProveedorNotFoundException {
        return proveedorService.deleteProveedor(CUIL);
    }

    @GetMapping("/valoraciongeneral/{CUIL}")
    public ValoracionGeneralResponse getValoracionByCUIL(@PathVariable("CUIL") Long CUIL) throws ProveedorNotFoundException, ValoracionNotFoundException, CompraNotFoundException {
        return proveedorService.getValoracionGeneral(CUIL);
    }


    @GetMapping("/test-notificacion")
    public void testNotificacion() {
        proveedorService.notificarProveedoresContratoPorExpirar();
    }
}
