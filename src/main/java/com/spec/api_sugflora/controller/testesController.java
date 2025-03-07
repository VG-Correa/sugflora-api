package com.spec.api_sugflora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.model.Usuario;

@RestController
@RequestMapping("api/teste")
public class testesController {

    @GetMapping("")
    public ResponseEntity<Object> getTeste(){
        Usuario user = new Usuario();
        user.setNome("Victor");
        user.setEmail("v.teste@teste.com");

        return ResponseEntity.ok().body(user.toDTO());
    }

}
