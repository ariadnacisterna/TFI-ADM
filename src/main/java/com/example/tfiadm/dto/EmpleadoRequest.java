package com.example.tfiadm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EmpleadoRequest {
    @JsonProperty("CUIL")
    private Long CUIL;
    private String nombre_completo;
    private String direccion;
    @JsonProperty("fecha_nacimiento")
    private LocalDate bod;
    private String mail;
    @JsonProperty("localidad_id")
    private Integer localidadId;
}
