package com.example.tfiadm.controller;

import com.example.tfiadm.dto.LoginRequest;
import com.example.tfiadm.dto.LoginResponse;
import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.repository.EmpleadoRepository;
import com.itextpdf.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/adm/login")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @PostMapping()
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Validación básica del LoginRequest
        if (loginRequest.getMail() == null || loginRequest.getCuil() == null) {
            return ResponseEntity.badRequest().body(new LoginResponse("Correo electrónico y CUIL son obligatorios", false));
        }

        // Buscar al empleado por el correo electrónico
        Empleado empleado = empleadoRepository.findByMail(loginRequest.getMail())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Comparar el CUIL con la contraseña (en este caso, el CUIL es la contraseña)
        if (!empleado.getCuil().toString().equals(loginRequest.getCuil())) {
            return ResponseEntity.status(401).body(new LoginResponse("Credenciales inválidas", false));
        }

        // Autenticación exitosa
        Authentication authentication = new UsernamePasswordAuthenticationToken(empleado.getMail(), empleado.getCuil());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Respuesta con éxito
        return ResponseEntity.ok(new LoginResponse("Inicio de sesión exitoso", true));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.IOException {
        request.logout();  // Cierra la sesión
        response.sendRedirect("/api/adm/login");  // Redirige a la página de login
        return null;
    }

}
