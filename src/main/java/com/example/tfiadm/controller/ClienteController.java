package com.example.tfiadm.controller;

import com.example.tfiadm.dto.ClienteRequest;
import com.example.tfiadm.dto.ClienteResponse;
import com.example.tfiadm.model.Cliente;
import com.example.tfiadm.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/adm/cliente")
@RequiredArgsConstructor

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@RequestBody ClienteRequest clienteRequest) {
        try {
            clienteService.crearCliente(clienteRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("El cliente fue creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/{cuil}")
    public ResponseEntity<String> modificarCliente(@PathVariable("cuil") Long cuil, @RequestBody ClienteRequest clienteRequest){
        try {
            clienteService.modificarCliente(cuil, clienteRequest);
            return ResponseEntity.status(HttpStatus.OK).body("El cliente fue modificado con éxito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{cuil}")
    public ResponseEntity<String> eliminarCliente(@PathVariable("cuil") Long cuil){
        try{
            clienteService.eliminarCliente(cuil);
            return ResponseEntity.status(HttpStatus.OK).body("El cliente fue eliminado con éxito");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{cuil}")
    public ResponseEntity<ClienteResponse> verificarCliente(@PathVariable Long cuil) throws Exception {
        Cliente cliente = clienteService.buscarCliente(cuil);
        ClienteResponse response = new ClienteResponse(cliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/todos")
    public List<ClienteResponse> listarClientes(){
        return clienteService.buscarClientes();
    }

}
