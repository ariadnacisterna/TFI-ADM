package com.example.tfiadm.service;


import com.example.tfiadm.dto.EmpleadoRequest;
import com.example.tfiadm.dto.EmpleadoResponse;
import com.example.tfiadm.exception.CUILAlreadyInUseException;
import com.example.tfiadm.exception.EmpleadoNotFoundException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.LocalidadNotFoundException;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.model.Localidad;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.example.tfiadm.repository.LocalidadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionUsageException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final LocalidadRepository localidadRepository;

    public EmpleadoResponse create(EmpleadoRequest request) throws CUILAlreadyInUseException, LocalidadNotFoundException, ErrorSintaxisException {
        if (request.getCUIL() == null || request.getNombre_completo().isEmpty() || request.getDireccion().isEmpty() || request.getBod() == null || request.getMail().isEmpty() || request.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Localidad localidad = localidadRepository.findById(request.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

        if (empleadoRepository.findByCUIL(request.getCUIL()).isPresent()) {
            throw new CUILAlreadyInUseException("CUIL Already In Use");
        }

        var empleado = Empleado.builder()
                .CUIL(request.getCUIL())
                .nombre_completo(request.getNombre_completo())
                .direccion(request.getDireccion())
                .bod(request.getBod())
                .mail(request.getMail())
                .localidad(localidad)
                .build();

        Empleado savedEmpleado = empleadoRepository.save(empleado);
        return new EmpleadoResponse(savedEmpleado);
    }
    public List<EmpleadoResponse> getAll(){
        return empleadoRepository.findAllByBorradoFalse().stream()
                .map(EmpleadoResponse::new)
                .toList();
    }
    public EmpleadoResponse getEmpleadoByCUIL(Long CUIL) throws EmpleadoNotFoundException {
        Empleado empleado = empleadoRepository.findByCUILAndBorradoFalse(CUIL)
                .orElseThrow(() -> new EmpleadoNotFoundException("Empleado con CUIL " + CUIL + " no encontrado o borrado"));

        return new EmpleadoResponse(empleado);
    }
    @Transactional
    public EmpleadoResponse updateEmpleado(Long CUIL, EmpleadoRequest request) throws EmpleadoNotFoundException, ErrorSintaxisException, LocalidadNotFoundException {
        if (request.getNombre_completo().isEmpty() || request.getDireccion().isEmpty() || request.getBod() == null || request.getMail().isEmpty() || request.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Empleado empleado =empleadoRepository.findByCUIL(CUIL)
                .orElseThrow(() -> new EmpleadoNotFoundException("Empleado Not Found"));

        Localidad localidad = localidadRepository.findById(request.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

                empleado.setNombre_completo(request.getNombre_completo());
                empleado.setDireccion(request.getDireccion());
                empleado.setLocalidad(localidad);
                empleado.setMail(request.getMail());
                empleado.setBod(request.getBod());

                Empleado updatedEmpleado = empleadoRepository.save(empleado);

                return new EmpleadoResponse(updatedEmpleado);
    }
    @Transactional
    public EmpleadoResponse deleteEmpleado(Long CUIL) throws EmpleadoNotFoundException {
        Empleado empleado = empleadoRepository.findByCUIL(CUIL)
                .orElseThrow(() -> new EmpleadoNotFoundException("Empleado Not Found"));
         empleado.setBorrado(true);
         Empleado deletedEmpleado = empleadoRepository.save(empleado);
         return new EmpleadoResponse(deletedEmpleado);
    }

}
