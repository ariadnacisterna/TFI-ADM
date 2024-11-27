package com.example.tfiadm.service;

import com.example.tfiadm.dto.CompraRequest;
import com.example.tfiadm.dto.CompraResponse;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.ProveedorNotFoundException;
import com.example.tfiadm.model.Compra;
import com.example.tfiadm.model.Proveedor;
import com.example.tfiadm.repository.CompraRepository;
import com.example.tfiadm.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;

    public CompraResponse create(CompraRequest request) throws ErrorSintaxisException, ProveedorNotFoundException {

        if (request.getProducto_servicio().isEmpty() || request.getTotal() == null || request.getFecha_compra() == null || request.getProveedorId() == null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Proveedor proveedor = proveedorRepository.findByIdproveedor(request.getProveedorId())
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));

        var compra = Compra.builder()
                .producto_servicio(request.getProducto_servicio())
                .total(request.getTotal())
                .fecha_compra(request.getFecha_compra())
                .proveedor(proveedor)
                .build();
        Compra saveCompra = compraRepository.save(compra);
        return new CompraResponse(saveCompra);
    }

    public List<CompraResponse> findAllByProveedorCUIL(long CUIL) throws ProveedorNotFoundException {
        Proveedor proveedor = proveedorRepository.findByCuil(CUIL)
                .orElseThrow(() -> new ProveedorNotFoundException("Empleado no encontrado"));

        List<Compra> compras = compraRepository.findByProveedor_Idproveedor(proveedor.getIdproveedor());
        return compras.stream().map(CompraResponse::new)
                .collect(Collectors.toList());
    }
}
