package com.example.tfiadm.dto;

import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Venta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FacturaResponse {
    private Integer idfactura;
    private LocalDate fecha;
    private String tipo;
    private Double total;
    private Boolean estado;
    @JsonProperty("venta_id")
    private Integer venta;

    public FacturaResponse(Factura factura) {
        this.idfactura = factura.getIdfactura();
        this.fecha = factura.getFecha();
        this.tipo = factura.getTipo();
        this.total = factura.getTotal();
        this.estado = factura.getEstado();
        this.venta = factura.getVenta().getIdventa();
    }
}
