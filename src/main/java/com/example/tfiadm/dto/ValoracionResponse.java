package com.example.tfiadm.dto;

import com.example.tfiadm.model.Valoracion;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ValoracionResponse {
    private Integer idvaloracion;

    private Integer calidad;

    private Integer puntualidad;

    private Integer cumplimiento;

    private String notas;

    public ValoracionResponse(Valoracion valoracion) {
        this.idvaloracion = valoracion.getIdvaloracion();
        this.calidad = valoracion.getCalidad();
        this.puntualidad = valoracion.getPuntualidad();
        this.cumplimiento = valoracion.getCumplimiento();
        this.notas = valoracion.getNotas();
    }

}
