package com.example.tfiadm.dto;

import com.example.tfiadm.model.Compra;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CompraResponse {
    private Integer idcompra;

    private String producto_servicio;

    private Integer total;

    private LocalDate fecha_compra;

    @JsonProperty("proveedor_id")
    private ProveedorResponse proveedor;

    public CompraResponse(Compra compra) {
        this.idcompra = compra.getIdcompra();
        this.producto_servicio = compra.getProducto_servicio();
        this.total = compra.getTotal();
        this.fecha_compra = compra.getFecha_compra();
        this.proveedor = new ProveedorResponse(compra.getProveedor());
    }
}
