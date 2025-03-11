package com.spec.api_sugflora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com username: " + username);
        }

        return usuario;
    }

}
