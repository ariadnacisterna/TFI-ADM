package com.example.tfiadm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VentaRequest {
    @JsonProperty("cliente_CUIL")
    private Long clienteCUIL;

    @JsonProperty("empleado_CUIL")
    private Long empleadoCUIL;

}
