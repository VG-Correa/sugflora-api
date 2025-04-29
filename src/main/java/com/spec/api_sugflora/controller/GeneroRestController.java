package com.spec.api_sugflora.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.GeneroDTO;
import com.spec.api_sugflora.dto.GeneroWriteDTO;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.responses.GenericResponse;

@RestController
@RequestMapping("api/genero")
public class GeneroRestController extends GenericRestController {

    @Autowired
    FamiliaService familiaService;

    @Autowired
    GeneroService generoService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody GeneroWriteDTO generoWriteDTO) {
        try {
            System.err.println(generoWriteDTO);

            Genero genero = new Genero(generoWriteDTO);
            genero.setId(null);
            genero.setFamilia(familiaService.findByIdOrThrow(generoWriteDTO.getFamilia_id()));

            Genero generoSaved = generoService.save(genero);
            return getResponseCreated("Genero criado com sucesso", generoSaved);

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable Integer id) {
        try {
            
            Genero genero = generoService.findByIdOrThrow(id);
            return getResponseOK(null, genero.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("familia/{id_familia}")
    public ResponseEntity<GenericResponse> getByFamiliaId(@PathVariable Integer id_familia) {

        try {
            List<Genero> generos = generoService.findAllByFamiliaId(id_familia);
            return getResponseOK(null, generos, Map.of("total_items", generos.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody GeneroWriteDTO generoWriteDTO) {
        try {
            
            Genero genero = generoService.findByIdOrThrow(generoWriteDTO.getId());
            GeneroDTO backup = genero.toDTO();

            Genero updated = generoService.update(generoWriteDTO);

            return getResponseOK("Gênero atualizado com sucesso", updated.toDTO(), Map.of("backup", backup));

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {
        try {
            
            Genero backup = generoService.deleteById(id);
            return getResponseDeleted("Genero deletado com sucesso", backup.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @PutMapping("reactive/{id}")
    public ResponseEntity<GenericResponse> reactive(@PathVariable Integer id) {
        try {
            generoService.reactiveById(id);
            return getResponseOK("Genero reativado com sucesso");
        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    
}
