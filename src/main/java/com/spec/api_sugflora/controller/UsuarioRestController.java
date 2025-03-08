package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    

    @PostMapping("")
    public ResponseEntity<Usuario> create(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        
        // TODO: Parei aqui

        return entity;
    }
    

}
