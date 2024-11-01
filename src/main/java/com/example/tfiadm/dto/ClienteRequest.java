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

public class ClienteRequest {
    @JsonProperty("CUIL")
    private Long cuil;
    private Long dni;
    private String nombre_completo;
    private String direccion;
    private String mail;
    @JsonProperty("fecha_nacimiento")
    private LocalDate bod;
    private Long telefono;
    private String condicion_impositiva;
    @JsonProperty("localidad_id")
    private Integer localidadId;


}