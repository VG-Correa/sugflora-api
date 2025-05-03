package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.spec.api_sugflora.dto.CampoDTO;
import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.service.CampoService;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.exception.EntityNotFoundException;
import com.spec.speedspring.core.responses.GenericResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody CampoWriteDTO campoWriteDTO) {

        try {

            Campo campo = new Campo(campoWriteDTO);

            Usuario responsavel = usuarioService.findByIdOrThrow(campoWriteDTO.getUsuario_responsavel_uuid());

            campo.setResponsavel(responsavel);
            Projeto projeto = projetoService.findById(campoWriteDTO.getProjeto_id());
            campo.setProjeto(projeto);

            Campo saved = campoService.save(campo);

            if (saved == null) {
                return getResponseInternalError();
            }

            return getResponseOK(
                    "Campo cadastrado com sucesso",
                    saved.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("usuario/{id_usuario}")
    public ResponseEntity<GenericResponse> getAllByResponsavelId(@PathVariable UUID id_usuario) {
        try {
            Usuario usuario = usuarioService.findByIdOrThrow(id_usuario);

            List<Campo> campos = campoService.findAllByUsuarioId(id_usuario);
            List<CampoDTO> camposDtos = campos.stream().map(campo -> campo.toDTO()).toList();

            return getResponseOK(
                    null,
                    camposDtos);

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @GetMapping("projeto/{id_projeto}")
    public ResponseEntity<GenericResponse> getAllByProjetoId(@PathVariable Integer id_projeto) {

        try {

            List<Campo> campos = campoService.findAllByProjetoId(id_projeto);
            return getResponseOK(null, toDTO(campos), Map.of("total_items", campos.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody CampoWriteDTO campoWriteDTO) {

        try {

            CampoDTO updated = campoService.update(campoWriteDTO);

            return getResponseOK("Campo atualizado com sucesso",
                    null,
                    Map.of("beckup", updated));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {

        try {
            CampoDTO beckup = campoService.delete(id);
            return getResponseOK("Campo deletado com sucesso", null, Map.of("beckup", beckup));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

}
