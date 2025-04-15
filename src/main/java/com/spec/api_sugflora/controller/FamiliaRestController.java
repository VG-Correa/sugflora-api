package com.spec.api_sugflora.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.FamiliaDTO;
import com.spec.api_sugflora.dto.FamiliaWriteDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadExistsException;
import com.spec.api_sugflora.exceptions.EntityInvalidException;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.service.FamiliaService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/familia")
public class FamiliaRestController extends GenericRestController {

    @Autowired
    FamiliaService familiaService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody FamiliaWriteDTO familiaWriteDTO) {

        try {

            Familia familia = new Familia(familiaWriteDTO);
            Familia familiaSaved = familiaService.save(familia);

            if (familiaSaved == null) {
                return getResponseInternalError(new Exception("Familia não foi salva"));
            }

            return getResponseCreated("Família criada com sucesso", familia);

        } catch (EntityAlreadExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (EntityInvalidException e) {
            return getResponseInvalidEntity(e);
        } catch (NullPointerException e) {
            return getResponseInternalError(e);

        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable Integer id) {

        try {

            Familia familia = familiaService.findByIdOrThrow(id);

            return getResponseOK("Familia encontrada com sucesso", familia);

        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAll() {
        try {
            List<Familia> familias = familiaService.findAll();
            List<FamiliaDTO> familiasDTOs = toDTO(familias);

            return getResponseOK("Familias encontradas com sucesso", familiasDTOs,
                    Map.of("total_items", familiasDTOs.size()));

        } catch (Exception e) {
            return getResponseInternalError(e);
        }
    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody FamiliaWriteDTO familiaWriteDTO) {
        try {

            Familia familia = familiaService.update(familiaWriteDTO);

            if (familia == null) {
                return getResponseInternalError(new Exception("Familia não atualizada"));
            }

            return getResponseOK("Familia atualizada com sucesso", familia);

        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (EntityInvalidException e) {
            return getResponseInvalidEntity(e);
        } catch (NullPointerException e) {
            return getResponseInternalError(e);
        } catch (EntityAlreadExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {

        try {

            Familia familia = familiaService.findByIdOrThrow(id);
            familiaService.delete(familia);

            return getResponseDeleted("Familia deletada com sucesso", familia);

        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (EntityInvalidException e) {
            return getResponseInvalidEntity(e);
        } catch (NullPointerException e) {
            return getResponseInternalError(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }
}
