package com.example.tfiadm.dto;

import com.example.tfiadm.model.Pais;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PaisResponse {
    private Integer idpais;
    private String nombre;

    public PaisResponse(Pais pais) {
        this.idpais = pais.getIdpais();
        this.nombre = pais.getNombre();
    }
}
