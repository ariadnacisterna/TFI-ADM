package com.example.tfiadm.dto;

import com.example.tfiadm.model.Pais;
import com.example.tfiadm.model.Provincia;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProvinciaResponse {
    private Integer idprovincia;
    private String nombre;
    @JsonProperty("pais_idpais")
    private PaisResponse pais_idpais;

    public ProvinciaResponse(Provincia provincia) {
        this.idprovincia = provincia.getIdprovincia();
        this.nombre = provincia.getNombre();
        this.pais_idpais = new PaisResponse(provincia.getPais());
    }
}
