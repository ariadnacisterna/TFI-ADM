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
    private PaisResponse pais;


    public ProvinciaResponse(Provincia provincia) {
        this.idprovincia = provincia.getIdprovincia();
        this.nombre = provincia.getNombre();
        this.pais=new PaisResponse(provincia.getPais());
    }
}
