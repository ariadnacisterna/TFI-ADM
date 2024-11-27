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
@Table(name = "proveedor")

public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idproveedor;

    @Column(unique = true, nullable = false)
    private Long cuil;

    @Column(nullable = false, length = 45)
    private String nombre_completo;

    @Column(nullable = false, length = 45)
    private String direccion;

    @Column(nullable = false,length = 100)
    private String mail;

    @Column(nullable = false)
    private boolean borrado;

    @Column(nullable = false)
    private LocalDate contrato_inicio;

    @Column(nullable = false)
    private LocalDate contrato_fin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_idlocalidad")
    private Localidad localidad;
}
