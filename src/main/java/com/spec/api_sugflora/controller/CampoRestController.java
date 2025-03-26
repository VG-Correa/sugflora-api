package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.spec.api_sugflora.dto.CampoDTO;
import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.service.CampoService;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;

import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("api/campo")
public class CampoRestController extends GenericRestController {

    @Autowired
    CampoService campoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProjetoService projetoService;

    // @Autowired
    // GenericResponse response;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody CampoWriteDTO campoWriteDTO) {
        
        try {
            
            Campo campo = new Campo(campoWriteDTO);
            
            Usuario responsavel = usuarioService.findById(campoWriteDTO.getUsuario_responsavel_uuid());
            campo.setResponsavel(responsavel);

            Projeto projeto = projetoService.findById(campoWriteDTO.getProjeto_id());
            campo.setProjeto(projeto);

            Campo saved = campoService.save(campo);

            if (saved == null) {
                return getResponseInternalError();
            }

            return getResponseOK(
                "Campo cadastrado com sucesso",
                saved.toDTO()
            );         

        } catch (EntityExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }
        
    }
    
    // TODO: Fazer validações dos campos
    @GetMapping("usuario/{id_usuario}")
    public ResponseEntity<GenericResponse> getAll(@PathVariable UUID id_usuario) {
        try {
            Usuario usuario = usuarioService.findById(id_usuario);
            if (usuario == null) {
                throw new EntityNotFoundException("Não foi encontrado um usuário com este id");
            }

            List<Campo> campos = campoService.findAllByUsuarioId(id_usuario);
            List<CampoDTO> camposDtos = campos.stream().map(campo -> campo.toDTO()).toList();

            return getResponseOK(
                null,
                camposDtos
            );

        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e.getMessage());
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }
    

}
