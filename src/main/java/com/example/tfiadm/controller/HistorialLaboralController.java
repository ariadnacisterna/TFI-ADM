package com.example.tfiadm.controller;

import com.example.tfiadm.dto.EmpleadoRequest;
import com.example.tfiadm.dto.EmpleadoResponse;
import com.example.tfiadm.dto.HistorialLaboralRequest;
import com.example.tfiadm.dto.HistorialLaboralResponse;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.HistorialLaboralNotFoundException;
import com.example.tfiadm.exception.LocalidadNotFoundException;
import com.example.tfiadm.service.HistorialLaboralService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/historialLaboral")
@RequiredArgsConstructor

public class HistorialLaboralController {
    private final HistorialLaboralService historialLaboralService;

    @PostMapping()
    public HistorialLaboralResponse create(@RequestBody HistorialLaboralRequest request) throws ErrorSintaxisException {
        return historialLaboralService.create(request);
    }

    @GetMapping("/{id}")
    public List<HistorialLaboralResponse> findAllByEmpleadoId(@PathVariable Integer id) throws EmpleadoNotFoundException {
        return historialLaboralService.findAllByEmpleadoId(id);
    }

    @PutMapping("/{id}")
    public HistorialLaboralResponse update(@PathVariable Integer id, @RequestBody HistorialLaboralRequest request) throws EmpleadoNotFoundException, ErrorSintaxisException, HistorialLaboralNotFoundException {
        return historialLaboralService.update(id, request);
    }
}
