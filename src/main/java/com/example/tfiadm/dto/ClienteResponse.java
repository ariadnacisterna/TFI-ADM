package com.example.tfiadm.dto;

import com.example.tfiadm.model.Cliente;
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

public class ClienteResponse {
    private Integer idcliente;
    private Long CUIL;
    private Long DNI;
    private String nombre_completo;
    private String direccion;
    private String mail;
    private boolean borrado;
    @JsonProperty("fecha_nacimiento")
    private LocalDate bod;
    private Long telefono;
    private String condicion_impositiva;
    @JsonProperty("localidad_id")
    private LocalidadResponse localidad;

    public ClienteResponse(Cliente cliente) {
        this.idcliente = cliente.getIdcliente();
        this.CUIL = cliente.getCuil();
        this.DNI = cliente.getDni();
        this.nombre_completo = cliente.getNombre_completo();
        this.direccion = cliente.getDireccion();
        this.mail = cliente.getMail();
        this.bod = cliente.getBod();
        this.telefono = cliente.getTelefono();
        this.condicion_impositiva = cliente.getCondicion_impositiva();
        this.localidad = new LocalidadResponse(cliente.getLocalidad());
    }
}
