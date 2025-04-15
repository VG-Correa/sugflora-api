package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.multipart.MultipartFile;

import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.dto.ProjetoWriteDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadActiveException;
import com.spec.api_sugflora.exceptions.EntityAlreadyDeletedException;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.EntityAlreadyActiveResponse;
import com.spec.api_sugflora.model.responses.EntityAlreadyDeletedResponse;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.model.responses.InternalErrorResponse;
import com.spec.api_sugflora.model.responses.NotFoundResponse;
import com.spec.api_sugflora.repository.ProjetoRepository;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;

import io.jsonwebtoken.io.IOException;
import io.micrometer.observation.transport.Propagator.Getter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/projeto")
public class ProjetoRestController extends GenericRestController{

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GenericResponse response;

    @Autowired
    ProjetoRepository projetoRepository;

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
                        .setData(projeto.toDTO());
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
            response = new InternalErrorResponse(e);
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
            response = new InternalErrorResponse(e);
            return ResponseEntity.badRequest().body(response.build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable Integer id) {

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
            response = new InternalErrorResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> UpdateProjeto(@RequestBody ProjetoWriteDTO projetoWriteDTO) {
        try {

            Projeto projeto = projetoService.findById(projetoWriteDTO.getId());
            if (projeto == null) {
                response = new NotFoundResponse(new Exception("Projeto não encontrado"));
                return ResponseEntity.status(response.getStatus()).body(response.build());
            }

            boolean usuario_dono_exists = usuarioService.userExistsById(projetoWriteDTO.getUsuario_dono_uuid());

            if (!usuario_dono_exists) {
                throw new EntityExistsException("Usuário dono do projeto não encontrado");
            }

            if (projetoWriteDTO.getUsuario_dono_uuid() == null) {
                response.setStatus(400)
                        .setError(true)
                        .setMessage("UUID do usuário não pode ser nullo");

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
            response = new InternalErrorResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @GetMapping("/usuario/{id_usuario}")
    public ResponseEntity<GenericResponse> getByUserId(@PathVariable UUID id_usuario) {

        try {

            List<Projeto> projetos = projetoService.findAllByUserId(id_usuario);
            List<ProjetoDTO> projetoDTOs = projetos.stream().map(Projeto::toDTO).toList();

            response.setError(false)
                    .setStatus(200)
                    .setData(projetoDTOs)
                    .setMetadata(Map.of("total_items", projetoDTOs.size()));

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (EntityExistsException e) {
            response = new NotFoundResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalErrorResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @DeleteMapping("force-delete/{id}")
    public ResponseEntity<GenericResponse> forceDeleteProjeto(@PathVariable Integer id) {

        try {

            ProjetoDTO beckup = projetoService.forceDeleteById(id);
            response.setStatus(200)
                    .setError(false)
                    .setData(beckup)
                    .setMessage("Projeto '" + beckup.getNome() + "' Deletado com sucesso");

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (EntityNotFoundException e) {
            response = new NotFoundResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        } catch (EntityAlreadyDeletedException e) {
            response = new EntityAlreadyDeletedResponse(e.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (Exception e) {
            response = new InternalErrorResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GenericResponse> deleteProjeto(@PathVariable Integer id) {

        try {

            Projeto beckup = projetoService.deleteById(id);
            response.setStatus(200)
                    .setError(false)
                    .setData(beckup.toDTO())
                    .setMessage("Projeto " + beckup.getNome() + "Deletado com sucesso");

            return ResponseEntity.status(response.getStatus()).body(response.build());

        } catch (EntityNotFoundException e) {
            response = new NotFoundResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        } catch (EntityAlreadyDeletedException e) {
            response = new EntityAlreadyDeletedResponse(e.getMessage());
            return ResponseEntity.status(response.getStatus()).body(response.build());
        } catch (Exception e) {
            response = new InternalErrorResponse(e);
            return ResponseEntity.status(response.getStatus()).body(response.build());
        }

    }

    @PutMapping("reactive/{id}")
    public ResponseEntity<GenericResponse> reactiveProjeto(@PathVariable Integer id) {
        
        try {
            projetoService.reactiveBYId(id);            

            return getResponseOK("Projeto ativado com sucesso");

        } catch (Exception e) {
            return getResponseException(e);
        }

    }
    

}
