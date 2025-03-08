package com.spec.api_sugflora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.IsNull;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.UsuarioRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        return usuario;
    }

    public boolean userExistsByEmail(String email) {
        boolean exist = findByEmail(email) != null ?  true :  false;
        return exist;
    }

    public Usuario findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
        return usuario;
    }

    public boolean userExistsByUsername(String username) {
        boolean exist = findByUsername(username) != null ? true : false;
        return exist;
    }

    public Usuario findByCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf).orElse(null);
        return usuario;
    }

    public boolean userExistsByCPF(String cpf) {
        boolean exist = findByCpf(cpf) != null ? true : false;
        return exist;
    }

    public Usuario findByRg(String rg) {
        Usuario usuario = usuarioRepository.findByRg(rg).orElse(null);
        return usuario;
    }

    public boolean userExistsByRg(String rg) {
        boolean exist = findByRg(rg) != null ? true : false;
        return exist;
    }

    public void isValid(Usuario usuario) {
        if (usuario.getCpf() == null) {
            
        }
    }

    public Usuario save(Usuario user) {
        if (userExistsByEmail(user.getEmail())) {
            throw new EntityExistsException("Este e-mail já está cadastrado");
        } else if (userExistsByUsername(user.getUsername())) {
            throw new EntityExistsException("Já existe um usuário com este username");
        } else if (userExistsByCPF(user.getCpf())) {
            throw new EntityExistsException("Já existe um usuário com este CPF");
        } else if (userExistsByRg(user.getRg())) {
            throw new EntityExistsException("Já existe um usuário com este RG");
        }

        Usuario novoUsuario = usuarioRepository.save(user);
        return novoUsuario;
    }

}
