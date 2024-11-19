package com.example.tfiadm.controller;

import com.example.tfiadm.dto.PaisResponse;
import com.example.tfiadm.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/adm")
@RequiredArgsConstructor
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping("/paises")
    public List<PaisResponse> listarPaises() {
        return paisService.buscarPaises(); // Llama al servicio para obtener la lista
    }
}
