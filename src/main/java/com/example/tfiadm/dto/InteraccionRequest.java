package com.example.tfiadm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteraccionRequest {
    private String tipo_interaccion;
    private String descripcion;
    private Long empleadoId;
    private Long clienteId;
}
