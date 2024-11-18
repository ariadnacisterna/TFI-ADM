package com.example.tfiadm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FacturaRequest {
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("venta_id")
    private Integer ventaId;
}
