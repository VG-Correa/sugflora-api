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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController extends GenericRestController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody UsuarioWriteDTO usuarioWriteDTO) {

        try {

            System.err.println("UsuarioWriteDTO: ");
            System.err.println(usuarioWriteDTO);
            Usuario usuario = new Usuario(usuarioWriteDTO);
            Usuario saved = usuarioService.save(usuario);

            return getResponseCreated("Usuário cadastrado com sucesso", saved.toDTO());

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

}
