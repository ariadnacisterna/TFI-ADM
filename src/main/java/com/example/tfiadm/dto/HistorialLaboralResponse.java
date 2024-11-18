package com.example.tfiadm.dto;

import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.HistorialLaboral;
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

public class HistorialLaboralResponse {
    private Integer idhistoriallaboral;
    @JsonProperty("fecha_inicio")
    private LocalDate fecha_inicio;
    @JsonProperty("fecha_fin")
    private LocalDate fecha_fin;
    @JsonProperty("rol")
    private String rol;
    @JsonProperty("lugar_trabajo")
    private String lugar_trabajo;

    public HistorialLaboralResponse(HistorialLaboral historialLaboral) {
        this.idhistoriallaboral=historialLaboral.getIdhistoriallaboral();
        this.fecha_inicio=historialLaboral.getFecha_inicio();
        this.fecha_fin=historialLaboral.getFecha_fin();
        this.rol=historialLaboral.getRol();
        this.lugar_trabajo=historialLaboral.getLugar_trabajo();
    }
}
