package com.example.tfiadm.service;

import com.example.tfiadm.dto.FacturaRequest;
import com.example.tfiadm.dto.FacturaResponse;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.FacturaNotFoundException;
import com.example.tfiadm.exception.VentaNotFoundException;
import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Venta;
import com.example.tfiadm.repository.FacturaRepository;
import com.example.tfiadm.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final VentaRepository ventaRepository;

    public FacturaResponse create(FacturaRequest request) throws ErrorSintaxisException, VentaNotFoundException {
        if(request.getTipo().isEmpty()){throw new ErrorSintaxisException("Debe ingresar Tipo de Factura");}

        Venta venta = ventaRepository.findByIdventa(request.getVentaId())
                .orElseThrow(()->new VentaNotFoundException("Venta no encontrada"));

        var factura = Factura.builder()
                .tipo(request.getTipo())
                .fecha(LocalDate.now())
                .estado(false)
                .venta(venta)
                .build();

        Factura savedFactura = facturaRepository.save(factura);
        return new FacturaResponse(savedFactura);

    }

    public FacturaResponse findByVentaId(Integer ventaId) throws ErrorSintaxisException, VentaNotFoundException {
        Factura factura = facturaRepository.findByVenta_Idventa(ventaId)
                .orElseThrow(()->new VentaNotFoundException("Venta no encontrada"));
        return new FacturaResponse(factura);
    }

    public FacturaResponse updatePago(Integer facturaId) throws FacturaNotFoundException {
        Factura factura = facturaRepository.findByIdfactura(facturaId)
                .orElseThrow(()->new FacturaNotFoundException("Factura no encontrada"));
                factura.setEstado(true);
                return new FacturaResponse(factura);
    }

}
