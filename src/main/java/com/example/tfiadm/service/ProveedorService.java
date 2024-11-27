package com.example.tfiadm.service;

import com.example.tfiadm.dto.*;
import com.example.tfiadm.exception.*;
import com.example.tfiadm.model.*;
import com.example.tfiadm.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorService {
    private final ValoracionRepository valoracionRepository;
    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final LocalidadRepository localidadRepository;
    private final EmailService emailService;
    private final EmpleadoRepository empleadoRepository;

    public ProveedorResponse create(ProveedorRequest request) throws CUILAlreadyInUseException, LocalidadNotFoundException, ErrorSintaxisException {
        if (request.getCUIL() == null || request.getNombre_completo().isEmpty() || request.getDireccion().isEmpty() || request.getContrato_inicio() == null || request.getMail().isEmpty()  || request.getContrato_fin() == null || request.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Localidad localidad = localidadRepository.findById(request.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

        if (proveedorRepository.findByCuil(request.getCUIL()).isPresent()) {
            throw new CUILAlreadyInUseException("CUIL Already In Use");
        }

        var proveedor = Proveedor.builder()
                .cuil(request.getCUIL())
                .nombre_completo(request.getNombre_completo())
                .direccion(request.getDireccion())
                .mail(request.getMail())
                .contrato_inicio(request.getContrato_inicio())
                .contrato_fin(request.getContrato_fin())
                .localidad(localidad)
                .build();

        Proveedor saveProveedor = proveedorRepository.save(proveedor);
        return new ProveedorResponse(saveProveedor);
    }

    public List<ProveedorResponse> getProveedoresContratoPorExpirarAndBorradoFalse() {
        LocalDate fechaLimite = LocalDate.now().plusMonths(1);
       return proveedorRepository.findProveedoresContratoCercaExpirarAndBorradoFalse(fechaLimite).stream().map(ProveedorResponse::new).toList();

    }

    public List<ProveedorResponse> getAll(){
        return proveedorRepository.findAllByBorradoFalse().stream()
                .map(ProveedorResponse::new)
                .toList();
    }

    public ProveedorResponse getProveedorByCUIL(Long CUIL) throws ProveedorNotFoundException {
        Proveedor proveedor = proveedorRepository.findByCuilAndBorradoFalse(CUIL)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor con CUIL " + CUIL + " no encontrado o borrado"));

        return new ProveedorResponse(proveedor);
    }
    @Transactional
    public ProveedorResponse updateProveedor(Long CUIL, ProveedorRequest request) throws ProveedorNotFoundException, ErrorSintaxisException, LocalidadNotFoundException {
        if (request.getCUIL() == null || request.getNombre_completo().isEmpty() || request.getDireccion().isEmpty() || request.getContrato_inicio() == null || request.getMail().isEmpty()  || request.getContrato_fin() == null || request.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Proveedor proveedor =proveedorRepository.findByCuil(CUIL)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor Not Found"));

        Localidad localidad = localidadRepository.findById(request.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

        proveedor.setNombre_completo(request.getNombre_completo());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setLocalidad(localidad);
        proveedor.setMail(request.getMail());
        proveedor.setContrato_inicio(request.getContrato_inicio());
        proveedor.setContrato_fin(request.getContrato_fin());

        Proveedor updatedProveedor = proveedorRepository.save(proveedor);

        return new ProveedorResponse(updatedProveedor);
    }

    @Transactional
    public ProveedorResponse deleteProveedor(Long CUIL) throws ProveedorNotFoundException {
        Proveedor proveedor = proveedorRepository.findByCuil(CUIL)
                .orElseThrow(() -> new ProveedorNotFoundException("Proveedor Not Found"));
        proveedor.setBorrado(true);
        Proveedor deletedProveedor = proveedorRepository.save(proveedor);
        return new ProveedorResponse(deletedProveedor);
    }

    public ValoracionGeneralResponse getValoracionGeneral(Long CUIL) throws ProveedorNotFoundException,ValoracionNotFoundException, CompraNotFoundException{
        Proveedor proveedor = proveedorRepository.findByCuil(CUIL).orElseThrow(() -> new ProveedorNotFoundException("Proveedor no encontrado"));
        List<Compra> compras = compraRepository.findByProveedor_Idproveedor(proveedor.getIdproveedor());

        if(compras.isEmpty()){throw new CompraNotFoundException("El proveedor no tiene compras");}

        List<Integer> compraIds = compras.stream()
                .map(Compra::getIdcompra)
                .collect(Collectors.toList());

        List<Valoracion> valoraciones = valoracionRepository.findByCompra_IdcompraIn(compraIds);
        if(valoraciones.isEmpty()){throw new ValoracionNotFoundException("El proveedor no tiene valoracion");}

        double promedioPuntualidad=valoraciones.stream()
                .mapToInt(Valoracion::getPuntualidad)
                .average()
                .orElse(0.0);

        double promedioCumplimiento=valoraciones.stream()
                .mapToInt(Valoracion::getCumplimiento)
                .average()
                .orElse(0.0);

        double promedioCalidad = valoraciones.stream()
                .mapToInt(Valoracion::getCalidad)
                .average()
                .orElse(0.0);
        return new ValoracionGeneralResponse(promedioPuntualidad,promedioCumplimiento,promedioCalidad);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void notificarProveedoresContratoPorExpirar() {
        LocalDate fechaLimite = LocalDate.now().plusMonths(1);
        List<Proveedor> proveedores = proveedorRepository.findProveedoresContratoCercaExpirarAndBorradoFalse(fechaLimite);
        List<Empleado> gerentes = empleadoRepository.findByEsGerenteTrue();

        for (Proveedor proveedor : proveedores) {
            for (Empleado gerente : gerentes) {
                String subject = "Notificación de Contrato Próximo a Expirar";
                String text = "Estimado " + gerente.getNombre_completo() + ",\n\n" +
                        "Le informamos que el contrato del proveedor " + proveedor.getNombre_completo() +
                        " está próximo a expirar el " + proveedor.getContrato_fin() + ".\n" +
                        "Saludos,\n" +
                        "El equipo de Administración";

                emailService.enviarMailConArchivo(
                        gerente.getMail(), subject, text, null, null);
            }
        }
    }

}
