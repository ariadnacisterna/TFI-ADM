package com.example.tfiadm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle")

public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer iddetalle;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private Integer subtotal;
    @ManyToOne
    @JoinColumn(name="factura_idfactura")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name="producto_idproducto")
    private Producto producto;

}
