package com.example.tfiadm.dto;

import com.example.tfiadm.model.Venta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VentaResponse {
    private Integer idventa;

    @JsonProperty("cliente_id")
    private ClienteResponse cliente;

    @JsonProperty("empleado_id")
    private EmpleadoResponse empleado;

    public VentaResponse(Venta venta) {
        this.idventa= venta.getIdventa();
        this.cliente = new ClienteResponse(venta.getCliente());
        this.empleado = new EmpleadoResponse(venta.getEmpleado());
    }
}
