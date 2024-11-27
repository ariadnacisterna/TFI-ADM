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
@Table(name = "compra")


public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idcompra;

    @Column(nullable = false, length = 45)
    private String producto_servicio;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private LocalDate fecha_compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_idproveedor")
    private Proveedor proveedor;
}
