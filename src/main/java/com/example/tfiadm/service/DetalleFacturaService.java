package com.example.tfiadm.service;

import com.example.tfiadm.dto.DetalleFacturaRequest;
import com.example.tfiadm.dto.DetalleFacturaResponse;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.exception.ProductoNotFoundException;
import com.example.tfiadm.exception.VentaNotFoundException;
import com.example.tfiadm.model.DetalleFactura;
import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Producto;
import com.example.tfiadm.model.Venta;
import com.example.tfiadm.repository.DetalleFacturaRepository;
import com.example.tfiadm.repository.FacturaRepository;
import com.example.tfiadm.repository.ProductoRepository;
import com.example.tfiadm.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DetalleFacturaService {
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final FacturaRepository facturaRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public DetalleFacturaResponse create(DetalleFacturaRequest request) throws ProductoNotFoundException, FacturaNotFoundException {
        Producto producto = productoRepository.findByIdproducto(request.getProductoId())
                .orElseThrow(()-> new ProductoNotFoundException("Producto no encontrado"));

        Venta venta = ventaRepository.findByIdventa(request.getVentaId())
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada"));


        var detalleFactura = DetalleFactura.builder()
                .producto(producto)
                .venta(venta)
                .cantidad(request.getCantidad())
                .subtotal(request.getCantidad()*producto.getPrecio_unitario())
                .build();

        DetalleFactura savedDetalleFactura = detalleFacturaRepository.save(detalleFactura);

        return new DetalleFacturaResponse(savedDetalleFactura);
    }
    public List<DetalleFacturaResponse> findAllByFacturaId(Integer facturaId) {
        return detalleFacturaRepository.findByFactura_Idfactura(facturaId).stream()
                .map(DetalleFacturaResponse::new)
                .toList();
    }
}