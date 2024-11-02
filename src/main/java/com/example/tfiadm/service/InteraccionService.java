
package com.example.tfiadm.service;

import com.example.tfiadm.dto.InteraccionRequest;
import com.example.tfiadm.dto.InteraccionResponse;
import com.example.tfiadm.model.Cliente;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.Interaccion;
import com.example.tfiadm.repository.ClienteRepository;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.example.tfiadm.repository.InteraccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InteraccionService {

    private final InteraccionRepository interaccionRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public InteraccionResponse crearInteraccion(InteraccionRequest interaccionRequest) {
        // Verifica que los IDs no sean nulos
        if (interaccionRequest.getEmpleadoId() == null) {
            throw new IllegalArgumentException("Los IDs de empleado no pueden ser nulos.");
        }

        if (interaccionRequest.getClienteId() == null) {
            throw new IllegalArgumentException("Los IDs de cliente no pueden ser nulos.");
        }

        // Busca al empleado
        Empleado empleado = empleadoRepository.findById(interaccionRequest.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Busca al cliente
        Cliente cliente = clienteRepository.findById(interaccionRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crea la interacción
        Interaccion interaccion = new Interaccion();
        interaccion.setTipo_interaccion(interaccionRequest.getTipo_interaccion());
        interaccion.setDescripcion(interaccionRequest.getDescripcion());
        interaccion.setFecha(LocalDate.now());
        interaccion.setEmpleado(empleado);
        interaccion.setCliente(cliente);

        // Guarda la interacción
        interaccionRepository.save(interaccion);

        // Prepara la respuesta
        InteraccionResponse response = new InteraccionResponse();
        response.setIdinteraccion(interaccion.getIdinteraccion());
        response.setTipo_interaccion(interaccion.getTipo_interaccion());
        response.setFecha(interaccion.getFecha());
        response.setDescripcion(interaccion.getDescripcion());
        response.setEmpleadoId(empleado.getIdempleado());
        response.setClienteId(cliente.getIdcliente()); // Asegúrate de incluir esto si es necesario

        return response;
    }

    public List<InteraccionResponse> consultarHistorial(Long clienteId) {
        List<Interaccion> interacciones = interaccionRepository.findByCliente_Idcliente(clienteId);
        if (interacciones.isEmpty()) {
            System.out.println("No se encontraron interacciones para el cliente con ID: " + clienteId);
        } else {
            interacciones.forEach(interaccion -> {
                System.out.println("Interacción encontrada: " + interaccion);
            });
        }

        return interacciones.stream()
                .map(interaccion -> new InteraccionResponse(interaccion)) // Usar el nuevo constructor
                .collect(Collectors.toList());
    }
}
