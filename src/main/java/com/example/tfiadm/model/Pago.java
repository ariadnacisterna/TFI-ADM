package com.example.tfiadm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")

public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idpago;


    @Column(nullable = false)
    private String metodo;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private LocalDate fecha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_idfactura")
    private Factura factura;
}
