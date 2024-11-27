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

public class HistorialLaboralRequest {
    private LocalDate fecha_inicio;

    private LocalDate fecha_fin;

    private String rol;

    private String lugar_trabajo;

    @JsonProperty ("empleado_id")
    private Integer empleadoid;
}
