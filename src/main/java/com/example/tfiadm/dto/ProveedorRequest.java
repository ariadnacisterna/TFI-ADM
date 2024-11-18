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

public class ProveedorRequest {
    @JsonProperty("CUIL")
    private Long CUIL;
    private String nombre_completo;
    private String direccion;
    private String mail;
    @JsonProperty("contrato_inicio")
    private LocalDate contrato_inicio;
    @JsonProperty("contrato_fin")
    private LocalDate contrato_fin;
    @JsonProperty("localidad_id")
    private Integer localidadId;
}
