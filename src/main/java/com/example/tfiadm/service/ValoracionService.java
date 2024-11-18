package com.example.tfiadm.service;

import com.example.tfiadm.dto.ValoracionRequest;
import com.example.tfiadm.dto.ValoracionResponse;
import com.example.tfiadm.exception.CompraNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.model.Compra;
import com.example.tfiadm.model.Valoracion;
import com.example.tfiadm.repository.CompraRepository;
import com.example.tfiadm.repository.ValoracionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ValoracionService {
    private final ValoracionRepository valoracionRepository;
    private final CompraRepository compraRepository;

    public ValoracionResponse create (ValoracionRequest request) throws ErrorSintaxisException, CompraNotFoundException {
        if(request.getCalidad() == null || request.getCalidad() == null || request.getPuntualidad() == null || request.getCompraId() == null){
            throw new ErrorSintaxisException("Todos los campos son obligatorios");
        }
        Compra compra = compraRepository.findByIdcompra(request.getCompraId())
                .orElseThrow(() -> new CompraNotFoundException("Compra no encontrada"));

        var valoracion = Valoracion.builder()
                .calidad(request.getCalidad())
                .puntualidad(request.getPuntualidad())
                .cumplimiento(request.getCumplimiento())
                .compra(compra)
                .build();
        Valoracion savedValoracion = valoracionRepository.save(valoracion);
        return new ValoracionResponse(savedValoracion);
    }

    public ValoracionResponse findByCompraId(Integer compraId) throws CompraNotFoundException {
        Valoracion valoracion = valoracionRepository.findByCompra_Idcompra(compraId)
                .orElseThrow(() -> new CompraNotFoundException("Compra no valorada o encontrada"));
        return new ValoracionResponse(valoracion);
    }


}
