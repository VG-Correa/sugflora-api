package com.spec.api_sugflora.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.UsuarioRepository;
import com.spec.speedspring.core.exception.EntityAlreadExistsException;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

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
        if (usuario.getCpf() == null || usuario.getCpf().isEmpty()) {
            throw new EntityInvalidException("CPF não pode ser nulo");
        } else if (usuario.getRg() == null || usuario.getRg().isEmpty()) {
            throw new EntityInvalidException("RG não pode ser nulo");
        } else if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new EntityInvalidException("E-mail não pode ser nulo");
        } else if (usuario.getUsername() == null || usuario.getUsername().isEmpty()) {
            throw new EntityInvalidException("Username não pode ser nulo");
        } else if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new EntityInvalidException("Senha não pode ser nula");
        } else if (usuario.getRole() == null || usuario.getRole().isEmpty()) {
            throw new EntityInvalidException("Role não pode ser nula");
        }
    }

    public Usuario save(Usuario user, PasswordEncoder passwordEncoder) {
        isValid(user);
        if (userExistsByEmail(user.getEmail())) {
            throw new EntityAlreadExistsException("Este e-mail já está cadastrado");
        } else if (userExistsByUsername(user.getUsername())) {
            throw new EntityAlreadExistsException("Já existe um usuário com este username");
        } else if (userExistsByCPF(user.getCpf())) {
            throw new EntityAlreadExistsException("Já existe um usuário com este CPF");
        } else if (userExistsByRg(user.getRg())) {
            throw new EntityAlreadExistsException("Já existe um usuário com este RG");
        }

        user.setSenha(passwordEncoder.encode(user.getSenha()));

        Usuario novoUsuario = usuarioRepository.save(user);
        return novoUsuario;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioDTO> findAllDTO() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream().map(Usuario::toDTO).toList();
        return usuariosDTO;
    }

    public Optional<Usuario> findById(UUID usuario_dono_uuid) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuario_dono_uuid);
        return usuario;
    }

    public boolean userExistsById(UUID usuario_dono_uuid) {
        return findById(usuario_dono_uuid) != null;
    }

    public Usuario findByIdOrThrow(UUID responsavel_id) {

        Usuario usuario = findById(responsavel_id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado"));
        return usuario;
    }

    @Transactional
    public Usuario update(UsuarioWriteDTO usuarioWriteDTO) {
        
        Usuario usuario = findByIdOrThrow(usuarioWriteDTO.getUuid());
        usuarioWriteDTO.setSenha(usuario.getSenha());
        
        usuario.initBy(usuarioWriteDTO);

        usuario.updateDateNow();

        isValid(usuario);
        return usuario;
    }

    @Transactional
    public void deleteById(UUID uuid) {

        Usuario usuario = findByIdOrThrow(uuid);

        usuario.setDeletedAt(LocalDateTime.now());
        usuario.setDeletedById(null);
        usuario.setDeleted(true);
        usuario.updateDateNow();
    }

}
