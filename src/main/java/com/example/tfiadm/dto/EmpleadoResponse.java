package com.example.tfiadm.dto;

import com.example.tfiadm.model.Empleado;
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

public class EmpleadoResponse {
    private Integer idempleado;
    private Long CUIL;
    private String nombre_completo;
    private String direccion;
    @JsonProperty("fecha_nacimiento")
    private LocalDate bod;
    private String mail;
    private boolean borrado;
    @JsonProperty("localidad_id")
    private LocalidadResponse localidad;

    public EmpleadoResponse(Empleado empleado) {
        this.idempleado = empleado.getIdempleado();
        this.CUIL = empleado.getCUIL();
        this.nombre_completo = empleado.getNombre_completo();
        this.direccion = empleado.getDireccion();
        this.bod = empleado.getBod();
        this.mail = empleado.getMail();
        this.localidad = new LocalidadResponse(empleado.getLocalidad());
    }
}
