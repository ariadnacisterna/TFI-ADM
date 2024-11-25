package com.example.tfiadm.dto;

import com.example.tfiadm.model.DetalleFactura;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DetalleFacturaResponse {
    private Integer iddetalle;
    private ProductoResponse producto;
    private Integer cantidad;
    private Double subtotal;

    public DetalleFacturaResponse(DetalleFactura detalle) {
        this.iddetalle = detalle.getIddetalle();
        this.producto = new ProductoResponse(detalle.getProducto());
        this.cantidad = detalle.getCantidad();
        this.subtotal = detalle.getSubtotal();
    }
}
