package com.example.tfiadm.security;

import com.example.tfiadm.model.Empleado;
import com.example.tfiadm.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoUserDetailsService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        return User.builder()
                .username(empleado.getMail())
                .password(empleado.getCuil().toString())
                .build();
    }
}
