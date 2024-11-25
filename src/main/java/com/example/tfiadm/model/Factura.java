package com.example.tfiadm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "factura")

public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idfactura;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column
    private Double total = 0.0;

    @Column(nullable = false)
    private Boolean estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_idventa")
    private Venta venta;

}
