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

public class ValoracionRequest {
    private Integer calidad;

    private Integer puntualidad;

    private Integer cumplimiento;

    private String notas;

    @JsonProperty("compra_id")
    private Integer compraId;
}
