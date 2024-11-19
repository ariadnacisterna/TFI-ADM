package com.example.tfiadm.service;

import com.example.tfiadm.dto.PaisResponse;
import com.example.tfiadm.model.Pais;
import com.example.tfiadm.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository paisRepository;

    public List<PaisResponse> buscarPaises() {
        List<Pais> paises = paisRepository.findAll(); // Recupera todos los países
        return paises.stream()
                .map(PaisResponse::new) // Convierte cada entidad Pais a un DTO PaisResponse
                .collect(Collectors.toList());
    }
}
