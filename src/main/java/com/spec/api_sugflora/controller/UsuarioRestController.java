package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.model.responses.InternalError;
import com.spec.api_sugflora.service.UsuarioService;

import jakarta.persistence.EntityExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GenericResponse response;

    public UsuarioRestController(){
        response = new GenericResponse();
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody UsuarioWriteDTO usuarioWriteDTO) {
        

        try {

            System.err.println("UsuarioWriteDTO: ");
            System.err.println(usuarioWriteDTO);
            Usuario usuario = new Usuario(usuarioWriteDTO);
            Usuario saved = usuarioService.save(usuario);

            response.setStatus(200)
                .setError(false)
                .setMessage("Usuario criado com sucesso")
                .setData(saved.toDTO());

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (IllegalArgumentException e) {
            response.setStatus(400)
                .setError(true)
                .setMessage(e.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (EntityExistsException e) {
            response.setStatus(HttpStatus.CONFLICT.value())
                .setError(true)
                .setMessage(e.getMessage());

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setError(true)
                .setMessage("Erro inesperado");
            
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll() {

        try {
            List<UsuarioDTO> usuarios = usuarioService.findAllDTO();

            response.setStatus(HttpStatus.OK.value())
                .setError(false)
                .setData(usuarios)
                .setMetadata(Map.of("total_items", usuarios.size()));

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalError();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.build());
        }

    }
    
}
