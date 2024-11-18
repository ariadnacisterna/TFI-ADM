package com.example.tfiadm.controller;

import com.example.tfiadm.dto.ValoracionRequest;
import com.example.tfiadm.dto.ValoracionResponse;
import com.example.tfiadm.exception.CompraNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.service.ValoracionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adm/valoracion")
@RequiredArgsConstructor

public class ValoracionController {

    private final ValoracionService valoracionService;

    @PostMapping()
    public ValoracionResponse create (@RequestBody ValoracionRequest request) throws CompraNotFoundException, ErrorSintaxisException {
        return valoracionService.create(request);
    }

    @GetMapping("/{id}")
    public ValoracionResponse findByCompraId(@PathVariable("id") Integer id)  throws CompraNotFoundException{
        return valoracionService.findByCompraId(id);
    }
}




