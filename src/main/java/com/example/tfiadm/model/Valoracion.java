package com.example.tfiadm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "valoracion")

public class Valoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idvaloracion;

    @Column(nullable = false)
    private Integer calidad;
    @Column(nullable = false)
    private Integer puntualidad;
    @Column(nullable = false)
    private Integer cumplimiento;
    @Column(length = 200)
    private String notas;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_idcompra")
    private Compra compra;
}
