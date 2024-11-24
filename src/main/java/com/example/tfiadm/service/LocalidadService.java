package com.example.tfiadm.service;

import com.example.tfiadm.dto.LocalidadResponse;
import com.example.tfiadm.model.Localidad;
import com.example.tfiadm.repository.LocalidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocalidadService {

    private final LocalidadRepository localidadRepository;

    public List<LocalidadResponse> obtenerLocalidadesPorProvincia(Integer provinciaId) {
        List<Localidad> localidades = localidadRepository.findByProvinciaIdprovincia(provinciaId);
        return localidades.stream()
                .map(LocalidadResponse::new)
                .collect(Collectors.toList());
    }
}
