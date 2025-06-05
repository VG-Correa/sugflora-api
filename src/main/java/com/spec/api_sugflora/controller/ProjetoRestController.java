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
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.ProjetoRepository;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.exception.EntityAlreadyDeletedException;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.responses.EntityAlreadyDeletedResponse;
import com.spec.speedspring.core.responses.GenericResponse;
import com.spec.speedspring.core.responses.InternalErrorResponse;
import com.spec.speedspring.core.responses.NotFoundResponse;

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
public class ProjetoRestController extends GenericRestController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProjetoRepository projetoRepository;

    @PostMapping("")
    public ResponseEntity<GenericResponse> novo(@RequestBody ProjetoWriteDTO projetoWriteDTO) {

        try {

            System.err.println(projetoWriteDTO);
            Projeto projeto = new Projeto(projetoWriteDTO);
            Usuario usuarioDono = usuarioService.findByIdOrThrow(projetoWriteDTO.getUsuario_dono_uuid());

            projeto.setDono(usuarioDono);

            System.out.println(projeto);
            Projeto saved = projetoService.save(projeto);

            if (saved != null) {
                return getResponseCreated("Projeto criado com sucesso", saved.toDTO());
            } else {
                return getResponseInternalError();
            }

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll() {
        try {
            List<Projeto> projetos = projetoService.findAll();
            return getResponseOK(null, toDTO(projetos), Map.of("total_items", projetos.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable Integer id) {

        try {
            Projeto projeto = projetoService.findByIdOrThrow(id);
            return getResponseOK("Projeto encontrado", projeto.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> UpdateProjeto(@RequestBody ProjetoWriteDTO projetoWriteDTO) {
        try {

            Projeto projeto = projetoService.findByIdOrThrow(projetoWriteDTO.getId());

            boolean usuario_dono_exists = usuarioService.userExistsById(projetoWriteDTO.getUsuario_dono_uuid());

            if (!usuario_dono_exists) {
                throw new EntityExistsException("Usuário dono do projeto não encontrado");
            }

            System.out.println("Atualizando PROJETO");
            System.out.println("Usuario id do banco: " + projeto.getDono().getId());
            System.out.println("Usuario id do DTO: " + projetoWriteDTO.getUsuario_dono_uuid());

            if (projetoWriteDTO.getUsuario_dono_uuid() != null
                    && !projeto.getDono().getId().equals(projetoWriteDTO.getUsuario_dono_uuid())) {
                return getResponseInvalidEntity(new EntityInvalidException(
                        "Não é possível atualizar um projeto para um usuário que não é dono"));

            } else if (projetoWriteDTO.getUsuario_dono_uuid() == null) {
                projetoWriteDTO.setUsuario_dono_uuid(projeto.getDono().getId());
            }

            ProjetoDTO backup = projeto.toDTO();
            projeto.initBy(projetoWriteDTO);

            projetoService.update(projeto);

            return getResponseOK("Projeto atualizado com sucesso", projeto.toDTO(), Map.of("backup", backup));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("/usuario/{id_usuario}")
    public ResponseEntity<GenericResponse> getByUserId(@PathVariable UUID id_usuario) {

        try {
            List<Projeto> projetos = projetoService.findAllByUserId(id_usuario);
            return getResponseOK(null, toDTO(projetos), Map.of("total_items", projetos.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @DeleteMapping("force-delete/{id}")
    public ResponseEntity<GenericResponse> forceDeleteProjeto(@PathVariable Integer id) {

        try {

            ProjetoDTO beckup = projetoService.forceDeleteById(id);
            return getResponseDeleted("Projeto " + beckup.getNome() + "deletado com sucesso", beckup);

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GenericResponse> deleteProjeto(@PathVariable Integer id) {

        try {

            Projeto beckup = projetoService.deleteById(id);
            return getResponseDeleted("Projeto " + beckup.getNome() + "deletado com sucesso", beckup);

        } catch (Exception e) {
            return getResponseException(e);
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
