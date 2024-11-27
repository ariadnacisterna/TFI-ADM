package com.example.tfiadm.service;


import com.example.tfiadm.model.Factura;
import com.example.tfiadm.model.Pago;
import com.example.tfiadm.model.Venta;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class GeneradorPDF {

    public byte[] generarFacturaPDF(Factura factura) throws IOException {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        Venta venta = factura.getVenta();

        Table headerTable = new Table(new float[]{1, 1});
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.addCell(new Cell().add(new Paragraph("AgroTecno\nCalle 123\nYerba Buena, Tucumán.")).setBorder(null));
        headerTable.addCell(new Cell().add(new Paragraph("Fecha: " + factura.getFecha() + "\nVenta ID: " + venta.getIdventa() + "\nFactura ID: " + factura.getIdfactura())).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        document.add(headerTable);

        document.add(new Paragraph("\nCUIL Cliente: " + venta.getCliente().getCuil() + "\nNombre Cliente: " + venta.getCliente().getNombre_completo()));

        Table table = new Table(new float[]{1, 3, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell(new Cell().add(new Paragraph("Cantidad")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Producto")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Precio Unitario")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Subtotal")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        venta.getDetallesFactura().forEach(detalle -> {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));
            table.addCell(new Cell().add(new Paragraph(detalle.getProducto().getDescripcion())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getProducto().getPrecio_unitario()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getSubtotal()))));
        });

        document.add(table);

        document.add(new Paragraph("\nTotal a pagar: " + factura.getTotal()).setTextAlignment(TextAlignment.RIGHT));

        document.close();

        return pdfOutputStream.toByteArray();
    }

    public byte[] generarPagoPDF(Pago pago) throws IOException {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(pdfOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);

        Factura factura = pago.getFactura();
        Venta venta = factura.getVenta();

        Table headerTable = new Table(new float[]{1, 1});
        headerTable.setWidth(UnitValue.createPercentValue(100));
        headerTable.addCell(new Cell().add(new Paragraph("AgroTecno\nCalle 123\nYerba Buena, Tucumán.")).setBorder(null));
        headerTable.addCell(new Cell().add(new Paragraph("Fecha: " + pago.getFecha() + "\nVenta ID: " + venta.getIdventa() + "\nFactura ID: " + factura.getIdfactura())).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        document.add(headerTable);

        document.add(new Paragraph("\nCUIL Cliente: " + venta.getCliente().getCuil() + "\nNombre Cliente: " + venta.getCliente().getNombre_completo()));

        document.add(new Paragraph("\nMétodo de Pago: " + pago.getMetodo()));
        document.add(new Paragraph("Total Pagado: " + pago.getTotal()));

        document.close();

        return pdfOutputStream.toByteArray();
    }
}
