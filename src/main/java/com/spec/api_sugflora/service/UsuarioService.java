package com.spec.api_sugflora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Usuario save(Usuario user) {
        Usuario novoUsuario = usuarioRepository.save(user);
        return novoUsuario;
    }

}
