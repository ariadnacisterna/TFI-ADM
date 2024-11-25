package com.example.tfiadm.dto;

import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Pago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PagoResponse {
    private Integer idpago;
    private String metodo;
    private Double total;
    private LocalDate fecha;
    private FacturaResponse factura;

    public PagoResponse(Pago pago) {
        this.idpago = pago.getIdpago();
        this.metodo= pago.getMetodo();
        this.total = pago.getTotal();
        this.fecha = pago.getFecha();
        this.factura = new FacturaResponse(pago.getFactura());
    }
}
