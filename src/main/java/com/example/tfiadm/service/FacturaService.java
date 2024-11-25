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

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final VentaRepository ventaRepository;
    private final GeneradorPDF generadorPDF;
    private final EmailService emailService;

    public FacturaResponse create(FacturaRequest request) throws ErrorSintaxisException, VentaNotFoundException, IOException {
        if (request.getTipo().isEmpty()) {
            throw new ErrorSintaxisException("Debe ingresar Tipo de Factura");
        }

        Venta venta = ventaRepository.findByIdventa(request.getVentaId())
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrada"));

        if (venta.getDetallesFactura().isEmpty()) {
            throw new ErrorSintaxisException("La venta no tiene detalles de factura");
        }

        var factura = Factura.builder()
                .tipo(request.getTipo())
                .fecha(LocalDate.now())
                .estado(false)
                .venta(venta)
                .build();

        Factura savedFactura = facturaRepository.save(factura);

        double nuevoTotal = venta.getDetallesFactura().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getProducto().getPrecio_unitario())
                .sum();
        savedFactura.setTotal(nuevoTotal);
        facturaRepository.save(savedFactura);

        byte[] pdfBytes = generadorPDF.generarFacturaPDF(savedFactura);

        try (FileOutputStream fos = new FileOutputStream("factura_" + savedFactura.getIdfactura() + ".pdf")){
            fos.write(pdfBytes);
        }

        String clienteEmail = venta.getCliente().getMail();
        emailService.enviarMailConArchivo(clienteEmail, "Factura Generada", "Estimado Cliente.\nAdjunto su factura.\nSaludos.", pdfBytes, "factura_" + savedFactura.getIdfactura() + ".pdf");

        return new FacturaResponse(savedFactura);
    }

    public FacturaResponse findByVentaId(Integer ventaId) throws FacturaNotFoundException {
        Factura factura = facturaRepository.findByVenta_Idventa(ventaId)
                .orElseThrow(() -> new FacturaNotFoundException("Factura no encontrada para la venta con ID: " + ventaId));
        return new FacturaResponse(factura);
    }

    public FacturaResponse updatePago(Integer facturaId) throws FacturaNotFoundException {
        Factura factura = facturaRepository.findByIdfactura(facturaId)
                .orElseThrow(() -> new FacturaNotFoundException("Factura no encontrada"));
        factura.setEstado(true);
        return new FacturaResponse(factura);
    }
}