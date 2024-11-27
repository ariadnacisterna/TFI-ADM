package com.example.tfiadm.controller;

import com.example.tfiadm.dto.InteraccionRequest;
import com.example.tfiadm.dto.InteraccionResponse;
import com.example.tfiadm.service.InteraccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/interaccion")
@RequiredArgsConstructor
public class InteraccionController {
    private final InteraccionService interaccionService;

    @PostMapping("/crear")
    public ResponseEntity<InteraccionResponse> crearInteraccion(@RequestBody InteraccionRequest interaccionRequest) {
        InteraccionResponse response = interaccionService.crearInteraccion(interaccionRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{clienteId}")
    public ResponseEntity<List<InteraccionResponse>> consultarHistorial(@PathVariable Long clienteId) {
        List<InteraccionResponse> historial = interaccionService.consultarHistorial(clienteId);
        return ResponseEntity.ok(historial);
    }
}
