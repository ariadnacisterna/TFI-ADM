package com.example.tfiadm.service;


import com.example.tfiadm.dto.HistorialLaboralRequest;
import com.example.tfiadm.dto.HistorialLaboralResponse;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;

import com.example.tfiadm.exception.HistorialLaboralNotFoundException;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.HistorialLaboral;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.example.tfiadm.repository.HistorialLaboralRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class HistorialLaboralService {
    private final HistorialLaboralRepository historialLaboralRepository;
    private final EmpleadoRepository empleadoRepository;

    @Transactional
    public HistorialLaboralResponse create (HistorialLaboralRequest request) throws ErrorSintaxisException {
       if (request.getFecha_inicio() == null || request.getRol().isEmpty() || request.getLugar_trabajo().isEmpty() || request.getEmpleadoid() == null){
            throw new ErrorSintaxisException("Fecha inicio, Rol, Lugar de trabajo y Id del empleado son necesarios.");
        }
        Empleado empleado = empleadoRepository.findByIdempleado(request.getEmpleadoid())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        var historialLaboral = HistorialLaboral.builder()
                .fecha_inicio(request.getFecha_inicio())
                .rol(request.getRol())
                .lugar_trabajo(request.getLugar_trabajo())
                .empleado(empleado)
                .build();

        HistorialLaboral savedHistorialLaboral = historialLaboralRepository.save(historialLaboral);
        return  new HistorialLaboralResponse(savedHistorialLaboral);
    }

    public List<HistorialLaboralResponse> findAllByEmpleadoId(Integer empleadoId) throws EmpleadoNotFoundException {
    Empleado empleado = empleadoRepository.findByIdempleado(empleadoId).orElseThrow(() -> new EmpleadoNotFoundException("Empleado no encontrado"));

    List<HistorialLaboral> historialesLaborales = historialLaboralRepository.findByEmpleado_Idempleado(empleadoId);
    return historialesLaborales.stream().map(HistorialLaboralResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public HistorialLaboralResponse update (Integer id, HistorialLaboralRequest request) throws EmpleadoNotFoundException, ErrorSintaxisException, HistorialLaboralNotFoundException {
        if (request.getFecha_inicio() == null || request.getFecha_fin() == null|| request.getRol().isEmpty() || request.getLugar_trabajo().isEmpty() || request.getEmpleadoid() == null){
            throw new ErrorSintaxisException("Fecha inicio, Fecha fin, Rol, Lugar de trabajo y Id del empleado son necesarios.");
        }
        if(empleadoRepository.findByIdempleado(request.getEmpleadoid()).isEmpty()){
            throw new EmpleadoNotFoundException("Empleado no encontrado");
        }
        HistorialLaboral historialLaboral = historialLaboralRepository.findByIdhistoriallaboral(id).orElseThrow(() -> new ErrorSintaxisException("Historial laboral no encontrado"));

        historialLaboral.setFecha_inicio(request.getFecha_inicio());
        historialLaboral.setRol(request.getRol());
        historialLaboral.setLugar_trabajo(request.getLugar_trabajo());
        historialLaboral.setFecha_fin(request.getFecha_fin());

        HistorialLaboral updatedHistorialLaboral = historialLaboralRepository.save(historialLaboral);
        return  new HistorialLaboralResponse(updatedHistorialLaboral);

    }
}
