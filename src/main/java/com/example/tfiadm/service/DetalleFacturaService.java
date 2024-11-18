package com.example.tfiadm.service;

import com.example.tfiadm.dto.DetalleFacturaRequest;
import com.example.tfiadm.dto.DetalleFacturaResponse;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.exception.ProductoNotFoundException;
import com.example.tfiadm.model.DetalleFactura;
import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Producto;
import com.example.tfiadm.repository.DetalleFacturaRepository;
import com.example.tfiadm.repository.FacturaRepository;
import com.example.tfiadm.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DetalleFacturaService {
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;

    public DetalleFacturaResponse create(DetalleFacturaRequest request) throws ProductoNotFoundException, FacturaNotFoundException {
        Producto producto = productoRepository.findByIdproducto(request.getProductoId())
                .orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado"));

        Factura factura = facturaRepository.findByIdfactura(request.getFacturaId())
                .orElseThrow(()-> new FacturaNotFoundException("Factura no encontrado"));

        if(factura.getTotal()==null)
        {
            factura.setTotal(0);
        }
        var detalleFactura = DetalleFactura.builder()
                .producto(producto)
                .factura(factura)
                .cantidad(request.getCantidad())
                .subtotal(request.getCantidad()*producto.getPrecio_unitario())
                .build();

        DetalleFactura savedDetalleFactura = detalleFacturaRepository.save(detalleFactura);

        Integer nuevoTotal= factura.getTotal()+savedDetalleFactura.getSubtotal();
        factura.setTotal(nuevoTotal);
        facturaRepository.save(factura);

        return new DetalleFacturaResponse(savedDetalleFactura);
    }
    public List<DetalleFacturaResponse> findAllByFacturaId(Integer facturaId) {
        return detalleFacturaRepository.findByFactura_Idfactura(facturaId).stream()
                .map(DetalleFacturaResponse::new)
                .toList();
    }
}
