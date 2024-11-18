package com.example.tfiadm.dto;

import com.example.tfiadm.model.Producto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductoResponse {
    private Integer idproducto;
    private String descripcion;
    @JsonProperty("precio_unitario")
    private Integer precio_unitario;

    public ProductoResponse(Producto producto) {
        this.idproducto=producto.getIdproducto();
        this.descripcion=producto.getDescripcion();
        this.precio_unitario=producto.getPrecio_unitario();
    }
}
