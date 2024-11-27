package com.example.tfiadm.dto;

import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.Proveedor;
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

public class ProveedorResponse {
    private Integer idproveedor;

    private Long cuil;

    private String nombre_completo;

    private String direccion;

    private String mail;

    @JsonProperty("contrato_inicio")
    private LocalDate contrato_inicio;

    @JsonProperty("contrato_fin")
    private LocalDate contrato_fin;

    private boolean borrado;

    @JsonProperty("localidad_id")
    private LocalidadResponse localidad;

    public ProveedorResponse(Proveedor proveedor) {
        this.idproveedor = proveedor.getIdproveedor();
        this.cuil = proveedor.getCuil();
        this.nombre_completo = proveedor.getNombre_completo();
        this.direccion = proveedor.getDireccion();
        this.mail = proveedor.getMail();
        this.contrato_inicio = proveedor.getContrato_inicio();
        this.contrato_fin = proveedor.getContrato_fin();
        this.localidad = new LocalidadResponse(proveedor.getLocalidad());
    }
}
