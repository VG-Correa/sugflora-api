package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.service.UsuarioService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.responses.GenericResponse;

import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController extends GenericRestController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody UsuarioWriteDTO usuarioWriteDTO) {

        try {

            System.err.println("UsuarioWriteDTO: ");
            System.err.println(usuarioWriteDTO);
            Usuario usuario = new Usuario(usuarioWriteDTO);
            Usuario saved = usuarioService.save(usuario, passwordEncoder);

            return getResponseCreated("Usuário cadastrado com sucesso", saved.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GenericResponse> getById(@PathVariable UUID uuid) {

        try {
            Usuario usuario = usuarioService.findByIdOrThrow(uuid);
            return getResponseOK(null, usuario.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll() {

        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return getResponseOK(null, toDTO(usuarios), Map.of("total_items", usuarios.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody UsuarioWriteDTO usuarioWriteDTO) {

        try {
            UsuarioDTO backup = usuarioService.findByIdOrThrow(usuarioWriteDTO.getUuid()).toDTO();
            Usuario saved = usuarioService.update(usuarioWriteDTO);

            return getResponseOK("Usuário atualizado com sucesso", saved.toDTO(), Map.of("backup", backup));

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID uuid) {

        try {
            usuarioService.deleteById(uuid);
            return getResponseOK("Usuário deletado com sucesso", null);

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("username/{username}")
    public ResponseEntity<GenericResponse> getByUserName(@PathVariable String username) {
        try {
            
            Usuario usuario = usuarioService.findByUserNameOrThrow(username);

            return getResponseOK(null, usuario.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

}
