package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.ProjetoWriteDTO;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.model.responses.InternalError;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/projeto")
public class ProjetoRestController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GenericResponse response;

    @PostMapping("")
    public ResponseEntity<GenericResponse> novo(@RequestBody ProjetoWriteDTO projetoWriteDTO) {

        try {

            System.err.println(projetoWriteDTO);
            Projeto projeto = new Projeto(projetoWriteDTO);
            Usuario usuarioDono = usuarioService.findById(projetoWriteDTO.getUsuario_dono_uuid());

            if (usuarioDono == null) {
                response.setStatus(400)
                        .setError(true)
                        .setMessage("Usuário dono do projeto não foi localizado");
                return ResponseEntity.badRequest().body(response.build());
            }

            projeto.setDono(usuarioDono);

            System.out.println(projeto);
            Projeto saved = projetoService.save(projeto);

            if (saved != null) {
                response.setStatus(209)
                        .setError(false)
                        .setMessage("Projeto cadastrado com sucesso")
                        .setData(projeto);
                return ResponseEntity.created(null).body(response.build());
            } else {
                response.setStatus(400)
                        .setError(false)
                        .setMessage("Erro ao cadastrar projeto");
                return ResponseEntity.badRequest().body(response.build());
            }

        } catch (Exception e) {
            response = new InternalError();
            return ResponseEntity.badRequest().body(response.build());
        }

    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll() {
        try {
            List<Projeto> projetos = projetoService.findAll();
            
            response.setStatus(200)
                .setMetadata(Map.of("total_items", projetos.size()))
                .setData(projetos.stream().map(Projeto::toDTO).toList());

            return ResponseEntity.ok().body(response.build());
        } catch (Exception e) {
            response = new InternalError();
            return ResponseEntity.badRequest().body(response.build());
        }
    }

}
