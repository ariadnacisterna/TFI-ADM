package com.example.tfiadm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "historial_labooral")

public class HistorialLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idhistorial_laboral")
    private Integer idhistoriallaboral;

    @Column
    private LocalDate fecha_inicio;

    @Column
    private LocalDate fecha_fin;

    @Column(nullable = false,length = 100)
    private String rol;

    @Column(nullable = false,length = 100)
    private String lugar_trabajo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "empleado_idempleado")
    private Empleado empleado;
}
