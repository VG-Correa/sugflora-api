package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.dto.ProjetoWriteDTO;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.model.responses.InternalError;
import com.spec.api_sugflora.repository.ProjetoRepository;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;

import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/projeto")
public class ProjetoRestController {

    private final ProjetoRepository projetoRepository;

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GenericResponse response;

    ProjetoRestController(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

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
                return ResponseEntity.status(201).body(response.build());
            } else {
                response.setStatus(400)
                        .setError(false)
                        .setMessage("Erro ao cadastrar projeto");
                return ResponseEntity.badRequest().body(response.build());
            }

        } catch (EntityExistsException entityExistsException) {
            response.setStatus(409)
                    .setError(true)
                    .setMessage(entityExistsException.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalError(e);
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
            response = new InternalError(e);
            return ResponseEntity.badRequest().body(response.build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@RequestParam Integer id) {

        try {
            Projeto projeto = projetoService.findById(id);

            if (projeto == null) {
                response.setStatus(404)
                        .setError(true)
                        .setMessage("Projeto não encontrado");
                return ResponseEntity.status(response.getStatus()).body(response.build());
            }

            response.setStatus(200)
                    .setMessage("Projeto localizado")
                    .setError(false)
                    .setData(projeto.toDTO());

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalError(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> UpdateProjeto(@RequestBody ProjetoWriteDTO projetoWriteDTO) {
        try {

            Projeto projeto = projetoService.findById(projetoWriteDTO.getId());
            if (projeto == null) {
                response.setStatus(404)
                        .setError(true)
                        .setMessage("Projeto não encontrado");
                return ResponseEntity.status(response.getStatus()).body(response.build());
            }

            if (projetoWriteDTO.getUsuario_dono_uuid() != null
                    && projeto.getDono().getId() != projetoWriteDTO.getUsuario_dono_uuid()) {
                response.setStatus(403)
                        .setError(true)
                        .setMessage("Impossível atrelar um projeto a um usuário que não é o dono");
                return ResponseEntity.status(response.getStatus()).body(response.build());
            } else if (projetoWriteDTO.getUsuario_dono_uuid() == null) {
                projetoWriteDTO.setUsuario_dono_uuid(projeto.getDono().getId());
            }

            ProjetoDTO beckup = projeto.toDTO();
            projeto.initBy(projetoWriteDTO);

            projetoService.update(projeto);

            response.setStatus(200)
                    .setError(false)
                    .setMessage("Projeto atualizado com sucesso")
                    .setData(projeto.toDTO())
                    .setMetadata(Map.of("beckup", beckup));

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (EntityExistsException entityExistsException) {
            response.setStatus(409)
                    .setError(true)
                    .setMessage(entityExistsException.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalError(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

}
