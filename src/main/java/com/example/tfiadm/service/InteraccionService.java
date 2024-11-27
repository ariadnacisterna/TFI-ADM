
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
        if (interaccionRequest.getEmpleado_cuil() == null) {
            throw new IllegalArgumentException("El cuil de empleado no pueden ser nulos.");
        }

        if (interaccionRequest.getCliente_cuil() == null) {
            throw new IllegalArgumentException("El cuil de cliente no pueden ser nulos.");
        }

        Empleado empleado = empleadoRepository.findByCuil(interaccionRequest.getEmpleado_cuil())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Cliente cliente = clienteRepository.findByCuil(interaccionRequest.getCliente_cuil())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Interaccion interaccion = new Interaccion();
        interaccion.setTipo_interaccion(interaccionRequest.getTipo_interaccion());
        interaccion.setDescripcion(interaccionRequest.getDescripcion());
        interaccion.setFecha(LocalDate.now());
        interaccion.setEmpleado(empleado);
        interaccion.setCliente(cliente);

        interaccionRepository.save(interaccion);

        InteraccionResponse response = new InteraccionResponse();
        response.setIdinteraccion(interaccion.getIdinteraccion());
        response.setTipo_interaccion(interaccion.getTipo_interaccion());
        response.setFecha(interaccion.getFecha());
        response.setDescripcion(interaccion.getDescripcion());
        response.setEmpleado_cuil(empleado.getCuil());
        response.setCliente_cuil(cliente.getCuil());

        return response;
    }

    public List<InteraccionResponse> consultarHistorial(Long cuilCliente) {
        List<Interaccion> interacciones = interaccionRepository.findByCliente_Cuil(cuilCliente);

        if (interacciones.isEmpty()) {
            throw new RuntimeException("No se encontraron interacciones para el cliente con cuil: " + cuilCliente);
        }

        return interacciones.stream()
                .map(interaccion -> new InteraccionResponse(
                        interaccion.getIdinteraccion(),
                        interaccion.getTipo_interaccion(),
                        interaccion.getDescripcion(),
                        interaccion.getFecha(),
                        interaccion.getEmpleado().getCuil(),
                        interaccion.getCliente().getCuil()))
                .collect(Collectors.toList());
    }

}
