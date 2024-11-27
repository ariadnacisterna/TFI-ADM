package com.example.tfiadm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class InicioSesionResponse {
    private EmpleadoResponse empleadoResponse;
}
