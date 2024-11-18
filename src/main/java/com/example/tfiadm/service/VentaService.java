package com.example.tfiadm.service;

import com.example.tfiadm.dto.CompraResponse;
import com.example.tfiadm.dto.VentaRequest;
import com.example.tfiadm.dto.VentaResponse;
import com.example.tfiadm.exception.*;
import com.example.tfiadm.model.*;
import com.example.tfiadm.repository.ClienteRepository;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.example.tfiadm.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public VentaResponse create(VentaRequest request) throws EmpleadoNotFoundException, ClienteNotFoundException, ErrorSintaxisException {
        if(request.getClienteCUIL() == null || request.getEmpleadoCUIL() == null) {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }
        Cliente cliente = clienteRepository.findByCuil(request.getClienteCUIL())
                 .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        Empleado empleado = empleadoRepository.findByCuil(request.getEmpleadoCUIL())
                .orElseThrow(() -> new EmpleadoNotFoundException("Empleado no encontrado"));

        var venta = Venta.builder()
                .cliente(cliente)
                .empleado(empleado)
                .build();

        Venta savedVenta = ventaRepository.save(venta);
        return new VentaResponse(savedVenta);
    }

    public List<VentaResponse> findAllByClienteCUIL(long CUIL) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findByCuil(CUIL)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));

        List<Venta> ventas = ventaRepository.findByCliente_idcliente(cliente.getIdcliente());
        return ventas.stream().map(VentaResponse::new)
                .collect(Collectors.toList());
    }

    public List<VentaResponse> findAllByEmpleadoCUIL(long CUIL) throws EmpleadoNotFoundException {
        Empleado empleado = empleadoRepository.findByCuil(CUIL)
                .orElseThrow(() -> new EmpleadoNotFoundException("Empleado no encontrado"));

        List<Venta> ventas = ventaRepository.findByEmpleado_idempleado(empleado.getIdempleado());
        return ventas.stream().map(VentaResponse::new)
                .collect(Collectors.toList());
    }
    public VentaResponse findById(Integer id) throws VentaNotFoundException {
        Venta venta = ventaRepository.findByIdventa(id)
                .orElseThrow(() -> new VentaNotFoundException("Venta no encontrado"));
        return new VentaResponse(venta);
    }
}
