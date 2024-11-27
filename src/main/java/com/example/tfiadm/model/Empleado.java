package com.example.tfiadm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleado")

public class Empleado {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(unique = true, nullable = false)
        private Integer idempleado;

        @Column(unique = true, nullable = false)
        private Long cuil;

        @Column(nullable = false, length = 45)
        private String nombre_completo;

        @Column(nullable = false, length = 45)
        private String direccion;

        @Column(nullable = false)
        private LocalDate bod;
<<<<<<< HEAD
        @Column(unique = true, nullable = false,length = 100)
=======

        @Column(nullable = false,length = 100)
>>>>>>> 3556b8b09cb73e0c5222ca712261f9e3300fa746
        private String mail;

        @Column(nullable = false)
        private boolean borrado;
<<<<<<< HEAD
        @ManyToOne(fetch = FetchType.EAGER)
=======

        @ManyToOne(fetch = FetchType.LAZY)
>>>>>>> 3556b8b09cb73e0c5222ca712261f9e3300fa746
        @JoinColumn(name = "localidad_idlocalidad")
        private Localidad localidad;

        @Column(nullable = false)
        private boolean esGerente;

        @OneToOne(mappedBy = "empleado", cascade = CascadeType.ALL)
        private InicioSesion inicioSesion;
}
