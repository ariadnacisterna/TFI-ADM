package com.example.tfiadm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DetalleFacturaRequest {
    @JsonProperty("producto_id")
    private Integer productoId;
    private Integer cantidad;
    @JsonProperty("factura_id")
    private Integer facturaId;
}
