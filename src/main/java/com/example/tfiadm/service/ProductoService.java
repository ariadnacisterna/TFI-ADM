package com.example.tfiadm.service;


import com.example.tfiadm.dto.ProductoResponse;
import com.example.tfiadm.exception.ProductoNotFoundException;
import com.example.tfiadm.model.Producto;
import com.example.tfiadm.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<ProductoResponse> findAll() {
        return productoRepository.findAll().stream()
                .map(ProductoResponse::new)
                .toList();
    }
    public ProductoResponse findById(Integer id) throws ProductoNotFoundException {
        Producto producto = productoRepository.findByIdproducto(id)
                .orElseThrow(()->new ProductoNotFoundException("Producto no encontrado"));
        return new ProductoResponse(producto);
    }
}
