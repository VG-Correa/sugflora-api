package com.spec.api_sugflora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.UsuarioRepository;
import com.spec.api_sugflora.security.SecurityConfiguration;

import jakarta.persistence.EntityExistsException;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SecurityConfiguration securityConfiguration;

    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        return usuario;
    }

    public boolean userExistsByEmail(String email) {
        boolean exist = findByEmail(email) != null ? true : false;
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
            throw new IllegalArgumentException("CPF não pode ser nulo");
        } else if (usuario.getRg() == null) {
            throw new IllegalArgumentException("RG não pode ser nulo");
        } else if (usuario.getEmail() == null) {
            throw new IllegalArgumentException("E-mail não pode ser nulo");
        } else if (usuario.getUsername() == null) {
            throw new IllegalArgumentException("Username não pode ser nulo");
        } else if (usuario.getSenha() == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        } else if (usuario.getRole() == null) {
            throw new IllegalArgumentException("Role não pode ser nula");
        }
    }

    public Usuario save(Usuario user) {
        isValid(user);
        if (userExistsByEmail(user.getEmail())) {
            throw new EntityExistsException("Este e-mail já está cadastrado");
        } else if (userExistsByUsername(user.getUsername())) {
            throw new EntityExistsException("Já existe um usuário com este username");
        } else if (userExistsByCPF(user.getCpf())) {
            throw new EntityExistsException("Já existe um usuário com este CPF");
        } else if (userExistsByRg(user.getRg())) {
            throw new EntityExistsException("Já existe um usuário com este RG");
        }

        user.setSenha(securityConfiguration.passwordEncoder().encode(user.getSenha()));

        Usuario novoUsuario = usuarioRepository.save(user);
        return novoUsuario;
    }

    public Object findAll() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioDTO> findAllDTO() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream().map(Usuario::toDTO).toList();
        return usuariosDTO;
    }

    public Usuario findById(UUID usuario_dono_uuid) {
        Usuario usuario = usuarioRepository.findById(usuario_dono_uuid).orElse(null);

        return usuario;
    }

}
