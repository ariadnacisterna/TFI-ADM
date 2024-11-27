package com.example.tfiadm.dto;

import com.example.tfiadm.model.Interaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteraccionResponse {
    private Integer idinteraccion;

    private String tipo_interaccion;

    private String descripcion;

    private LocalDate fecha;

    private Long empleado_cuil;

    private Long cliente_cuil;

    public InteraccionResponse(Interaccion interaccion) {
        this.idinteraccion = interaccion.getIdinteraccion();
        this.tipo_interaccion = interaccion.getTipo_interaccion();
        this.descripcion = interaccion.getDescripcion();
        this.fecha = interaccion.getFecha();
        this.empleado_cuil = interaccion.getEmpleado() != null ? interaccion.getEmpleado().getCuil() : null;
        this.cliente_cuil = interaccion.getCliente() != null ? interaccion.getCliente().getCuil() : null;
    }
}
