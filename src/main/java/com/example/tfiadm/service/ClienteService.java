package com.example.tfiadm.service;

import com.example.tfiadm.dto.ClienteRequest;
import com.example.tfiadm.dto.ClienteResponse;
import com.example.tfiadm.exception.CUILAlreadyInUseException;
import com.example.tfiadm.exception.ErrorSintaxisException;
import com.example.tfiadm.exception.LocalidadNotFoundException;
import com.example.tfiadm.model.Cliente;
import com.example.tfiadm.model.Localidad;
import com.example.tfiadm.repository.ClienteRepository;
import com.example.tfiadm.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    LocalidadRepository localidadRepository;

    public void crearCliente(ClienteRequest clienteRequest) throws Exception {
        Optional<Cliente> existeCliente = clienteRepository.findByCuil(clienteRequest.getCuil());

        if (clienteRequest.getCuil()  == null || clienteRequest.getNombre_completo().isEmpty() || clienteRequest.getDireccion().isEmpty() || clienteRequest.getBod() == null || clienteRequest.getMail().isEmpty() || clienteRequest.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Localidad localidad = localidadRepository.findById(clienteRequest.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

        if (clienteRepository.findByCuil(clienteRequest.getCuil()).isPresent()) {
            throw new CUILAlreadyInUseException("CUIL Already In Use");
        }

        if (existeCliente.isEmpty()) {
            Cliente clienteCreado = new Cliente();

            clienteCreado.setCuil(clienteRequest.getCuil());
            clienteCreado.setDni(clienteRequest.getDni());
            clienteCreado.setNombre_completo(clienteRequest.getNombre_completo());
            clienteCreado.setDireccion(clienteRequest.getDireccion());
            clienteCreado.setMail(clienteRequest.getMail());
            clienteCreado.setBod(clienteRequest.getBod());
            clienteCreado.setTelefono(clienteRequest.getTelefono());
            clienteCreado.setCondicion_impositiva(clienteRequest.getCondicion_impositiva());

            clienteCreado.setLocalidad(localidad);

            clienteRepository.save(clienteCreado);
        } else {
            throw new Exception("El cliente ya existe en el sistema.");
        }
    }


    public void modificarCliente(Long cuil, ClienteRequest clienteActualizado) throws Exception{
        Cliente clienteEncontrado = buscarCliente(cuil);

        if (clienteActualizado.getCuil()  == null || clienteActualizado.getNombre_completo().isEmpty() || clienteActualizado.getDireccion().isEmpty() || clienteActualizado.getBod() == null || clienteActualizado.getMail().isEmpty() || clienteActualizado.getLocalidadId()==null)  {
            throw new ErrorSintaxisException("Todos los campos son obligatorios.");
        }

        Localidad localidad = localidadRepository.findById(clienteActualizado.getLocalidadId())
                .orElseThrow(() -> new LocalidadNotFoundException("Localidad not Found"));

        clienteEncontrado.modificarCliente(
               clienteActualizado.getCuil(),
                clienteActualizado.getDni(),
                clienteActualizado.getNombre_completo(),
                clienteActualizado.getDireccion(),
                clienteActualizado.getMail(),
                clienteActualizado.getBod(),
                clienteActualizado.getTelefono(),
                clienteActualizado.getCondicion_impositiva(),
                clienteActualizado.getLocalidadId());
        clienteRepository.save(clienteEncontrado);

    }

    public void eliminarCliente(Long cuil) throws Exception {
        Cliente clienteEncontrado = buscarCliente(cuil);
        clienteEncontrado.bajaCliente();
        clienteRepository.save(clienteEncontrado);
    }

    public Cliente buscarCliente(Long cuil) throws Exception {
        return clienteRepository.findByCuilAndBorradoFalse(cuil)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
    }

    public List<ClienteResponse> buscarClientes()  {
        return clienteRepository.findAllByBorradoFalse().stream()
                .map(ClienteResponse::new)
                .toList();
    }
}
