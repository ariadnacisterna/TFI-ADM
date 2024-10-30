package com.example.tfiadm.controller;

import com.example.tfiadm.dto.EmpleadoRequest;
import com.example.tfiadm.dto.EmpleadoResponse;
import com.example.tfiadm.exception.CUILAlreadyInUseException;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.LocalidadNotFoundException;
import com.example.tfiadm.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adm/empleados")
@RequiredArgsConstructor

public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @PostMapping()
    public EmpleadoResponse create(@RequestBody EmpleadoRequest request) throws CUILAlreadyInUseException, LocalidadNotFoundException, ErrorSintaxisException {
        return empleadoService.create(request);
    }

    @GetMapping()
    public List<EmpleadoResponse> getAll() {
        return empleadoService.getAll();
    }

    @GetMapping("/{CUIL}")
    public EmpleadoResponse getEmpleadoByCUIL(@PathVariable Long CUIL) throws EmpleadoNotFoundException {
        return empleadoService.getEmpleadoByCUIL(CUIL);
    }

    @PutMapping("/{CUIL}")
    public EmpleadoResponse update(@PathVariable Long CUIL, @RequestBody EmpleadoRequest request) throws EmpleadoNotFoundException, LocalidadNotFoundException, ErrorSintaxisException {
        return empleadoService.updateEmpleado(CUIL, request);
    }
    @DeleteMapping("/{CUIL}")
    public EmpleadoResponse deleteEmpleado(@PathVariable Long CUIL) throws EmpleadoNotFoundException {
        return empleadoService.deleteEmpleado(CUIL);
    }
}
