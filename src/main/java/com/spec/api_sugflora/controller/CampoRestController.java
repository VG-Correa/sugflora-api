package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.spec.api_sugflora.dto.CampoDTO;
import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadExistsException;
import com.spec.api_sugflora.exceptions.EntityAlreadyDeletedException;
import com.spec.api_sugflora.exceptions.EntityInvalidException;
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
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




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

            if (responsavel == null) {
                throw new EntityNotFoundException("Responsável pelo campo não encontrado");
            }

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

        } catch (EntityAlreadExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }
        
    }
    
    @GetMapping("usuario/{id_usuario}")
    public ResponseEntity<GenericResponse> getAllByResponsavelId(@PathVariable UUID id_usuario) {
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
            return getResponseNotFound(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }
    
    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody CampoWriteDTO campoWriteDTO) {
        
        try {
            
            CampoDTO updated = campoService.update(campoWriteDTO);

            return getResponseOK("Campo atualizado com sucesso", 
                null, 
                Map.of("beckup", updated)
            );

        } catch (EntityAlreadExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (EntityInvalidException e) {
            return getResponseInvalidEntity(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }
        
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {

        try {
            CampoDTO beckup = campoService.delete(id);
            return getResponseOK("Campo deletado com sucesso", null, Map.of("beckup", beckup));
        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (EntityAlreadyDeletedException e) {
            return getResponseEntityAlreadyDeletedException(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }

}
