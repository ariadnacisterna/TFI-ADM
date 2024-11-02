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
@Table(name = "interaccion")

public class Interaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idinteraccion;

    @Column(nullable = false, length = 45)
    private String tipo_interaccion;

    @Column(nullable = false, length = 45)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_cuil")
    private Cliente cliente;

    public Interaccion(String tipo_interaccion, String descripcion, LocalDate fecha, Empleado empleado, Cliente cliente) {
        this.tipo_interaccion = tipo_interaccion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.empleado = empleado;
        this.cliente = cliente;
    }

}
