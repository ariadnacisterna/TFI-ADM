package com.example.tfiadm.dto;

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

public class CompraRequest {
    @JsonProperty("producto_servicio")
    private String producto_servicio;
    private Integer total;
    @JsonProperty("fecha_compra")
    private LocalDate fecha_compra;
    @JsonProperty("proveedor_id")
    private Integer proveedorId;

}
