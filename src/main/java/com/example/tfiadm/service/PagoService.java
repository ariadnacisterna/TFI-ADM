package com.example.tfiadm.service;

import com.example.tfiadm.dto.PagoRequest;
import com.example.tfiadm.dto.PagoResponse;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Pago;
import com.example.tfiadm.repository.FacturaRepository;
import com.example.tfiadm.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PagoService {
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;

    public PagoResponse create(PagoRequest request) throws FacturaNotFoundException, ErrorSintaxisException {
        if(request.getTotal() == null || request.getTotal() == null || request.getFacturaId()==null) {
            throw new ErrorSintaxisException("Todos los campos son obligatorios");
        }

        Factura factura = facturaRepository.findByIdfactura(request.getFacturaId())
                .orElseThrow(()-> new FacturaNotFoundException("Factura no encontrada"));

        var pago = Pago.builder()
                .total(request.getTotal())
                .metodo(request.getMetodo())
                .factura(factura)
                .build();

        Pago savedPago = pagoRepository.save(pago);
        if(savedPago.getTotal().equals(factura.getTotal())) {
            factura.setEstado(true);
        }
        facturaRepository.save(factura);
        return new PagoResponse(savedPago);
    }
    public PagoResponse findByIdFactura(Integer id) throws FacturaNotFoundException {
        Pago pago = pagoRepository.findByFactura_Idfactura(id)
                .orElseThrow(()-> new FacturaNotFoundException("Factura no encontrada"));
        return new PagoResponse(pago);
    }
}
