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

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class PagoService {
    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;
    private final GeneradorPDF generadorPDF;
    private final EmailService emailService;

    public PagoResponse create(PagoRequest request) throws FacturaNotFoundException, ErrorSintaxisException, IOException {
        if(request.getTotal() == null || request.getTotal() == null || request.getFacturaId()==null) {
            throw new ErrorSintaxisException("Todos los campos son obligatorios");
        }

        Factura factura = facturaRepository.findByIdfactura(request.getFacturaId())
                .orElseThrow(()-> new FacturaNotFoundException("Factura no encontrada"));

        var pago = Pago.builder()
                .total(request.getTotal())
                .metodo(request.getMetodo())
                .factura(factura)
                .fecha(LocalDate.now())
                .build();

        Pago savedPago = pagoRepository.save(pago);

        double totalPagado = pagoRepository.findByFactura_Idfactura(factura.getIdfactura()).stream()
                .mapToDouble(Pago::getTotal)
                .sum();

        if (totalPagado >= factura.getTotal()) {
            factura.setEstado(true);
        }

        facturaRepository.save(factura);

        byte[] pdfBytes = generadorPDF.generarPagoPDF(savedPago);

        try(FileOutputStream fos = new FileOutputStream("comprobante_pago" + savedPago.getIdpago() + ".pdf")){
            fos.write(pdfBytes);
        }

        String clienteEmail = factura.getVenta().getCliente().getMail();
        emailService.enviarMailConArchivo(clienteEmail, "Comprobante de Pago", "Estimado cliente,\nSe adjunta su comprobante de pago.\nSaludos.", pdfBytes, "comprobante_pago" + savedPago.getIdpago() + ".pdf");

        return new PagoResponse(savedPago);
    }
    public PagoResponse findByIdFactura(Integer id) throws FacturaNotFoundException {
        Pago pago = pagoRepository.findByFactura_Idfactura(id)
                .orElseThrow(()-> new FacturaNotFoundException("Factura no encontrada"));
        return new PagoResponse(pago);
    }
}
