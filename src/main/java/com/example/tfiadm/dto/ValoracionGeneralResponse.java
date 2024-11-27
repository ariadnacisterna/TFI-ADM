package com.example.tfiadm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor

public class ValoracionGeneralResponse {
    private double puntualidadPromedio;

    private double cumplimientoPromedio;

    private double calidadPromedio;

    public ValoracionGeneralResponse(double puntualidadPromedio, double cumplimientoPromedio, double calidadPromedio) {
        this.puntualidadPromedio = puntualidadPromedio;
        this.cumplimientoPromedio = cumplimientoPromedio;
        this.calidadPromedio = calidadPromedio;
    }

}
