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
@Table(name = "cliente")

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer idcliente;

    @Column(unique = true, nullable = false)
    private Long cuil;

    @Column(unique = true, nullable = false)
    private Long dni;

    @Column(nullable = false, length = 45)
    private String nombre_completo;

    @Column(nullable = false, length = 45)
    private String direccion;

    @Column(nullable = false,length = 100)
    private String mail;

    @Column(nullable = false)
    private boolean borrado;

    @Column(nullable = false)
    private LocalDate bod;

    @Column(nullable = false)
    private Long telefono ;

    @Column(nullable = false, length = 45)
    private String condicion_impositiva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_idlocalidad")
    private Localidad localidad;

    public Cliente(Long cuil, Long dni, String nombreCompleto, String direccion, String mail, boolean borrado, LocalDate bod, String condicionImpositiva, Localidad localidad) {
        this.cuil = cuil;
        this.dni = dni;
        this.nombre_completo = nombreCompleto;
        this.direccion = direccion;
        this.mail =  mail;
        this.borrado = borrado;
        this.bod = bod;
        this.condicion_impositiva = condicionImpositiva;
        this.localidad = localidad;
    }


    public void bajaCliente() {
        this.borrado = true;
    }

    public void modificarCliente(Long cuil, Long dni, String nombreCompleto, String direccion, String mail, LocalDate bod, Long telefono, String condicionImpositiva, Integer localidadId) {
        this.cuil = cuil;
        this.dni = dni;
        this.nombre_completo = nombreCompleto;
        this.direccion = direccion;
        this.mail = mail;
        this.borrado = borrado;
        this.bod = bod;
        this.telefono = telefono;
        this.condicion_impositiva = condicionImpositiva;
        this.localidad = localidad;
    }
}

