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
        private Long CUIL ;
        @Column(nullable = false, length = 45)
        private String nombre_completo;
        @Column(nullable = false, length = 45)
        private String direccion;
        @Column(nullable = false)
        private LocalDate bod;
        @Column(nullable = false,length = 100)
        private String mail;
        @Column(nullable = false)
        private boolean borrado;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "localidad_idlocalidad")
        private Localidad localidad;

}
