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

public class LocalidadRequest {
    private String nombre;
    @JsonProperty("provincia_idprovincia")
    private Integer provincia_idprovincia;
}
