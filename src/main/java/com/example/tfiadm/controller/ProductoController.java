package com.example.tfiadm.controller;

import com.example.tfiadm.dto.ProductoResponse;
import com.example.tfiadm.model.Producto;
import com.example.tfiadm.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adm/productos")
@RequiredArgsConstructor

public class ProductoController {
    private final ProductoService productoService;

    @GetMapping()
    public List<ProductoResponse> findAll() {return productoService.findAll();}

    @GetMapping("{id}")
    public ProductoResponse findById(Integer id) {return productoService.findById(id);}
}
