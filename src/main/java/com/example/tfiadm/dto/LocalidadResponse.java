package com.example.tfiadm.dto;

import com.example.tfiadm.model.Localidad;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LocalidadResponse {
    private Integer idlocalidad;
    private String nombre;
    @JsonProperty("provincia_idprovincia")
    private ProvinciaResponse provincia_idprovincia;

    public LocalidadResponse(Localidad localidad) {
        this.idlocalidad = localidad.getIdlocalidad();
        this.nombre = localidad.getNombre();
        this.provincia_idprovincia = new ProvinciaResponse(localidad.getProvincia());
    }
}
