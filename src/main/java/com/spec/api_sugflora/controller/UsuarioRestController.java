package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.service.UsuarioService;

import jakarta.persistence.EntityExistsException;

import java.util.HashMap;
import java.util.List;
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

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> create(@RequestBody UsuarioWriteDTO usuarioWriteDTO) {
        
        try {

            System.err.println("UsuarioWriteDTO: ");
            System.err.println(usuarioWriteDTO);
            Usuario usuario = new Usuario(usuarioWriteDTO);
            Usuario saved = usuarioService.save(usuario);

            HashMap<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário criado com sucesso");
            response.put("usuario", saved.toDTO());


            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("Erro", "Dados inválidos");
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (EntityExistsException e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("Erro", "Conflito de dados");
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

        } catch (Exception e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("Erro", "Erro interno");
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getAll() {

        try {
            List<UsuarioDTO> usuarios = usuarioService.findAllDTO();

            HashMap<String, Object> response = new HashMap<>();
            response.put("total", usuarios.size());
            response.put("usuarios", usuarios);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("Erro", "Erro interno");
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    
}
