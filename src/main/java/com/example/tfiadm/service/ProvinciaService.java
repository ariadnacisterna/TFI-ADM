package com.example.tfiadm.service;

import com.example.tfiadm.dto.ProvinciaResponse;
import com.example.tfiadm.model.Provincia;
import com.example.tfiadm.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvinciaService {

    private final ProvinciaRepository provinciaRepository;

    public List<ProvinciaResponse> obtenerProvinciasPorPais(Integer paisId) {
        List<Provincia> provincias = provinciaRepository.findByPaisIdpais(paisId);
        return provincias.stream()
                .map(ProvinciaResponse::new)
                .collect(Collectors.toList());
    }
}
