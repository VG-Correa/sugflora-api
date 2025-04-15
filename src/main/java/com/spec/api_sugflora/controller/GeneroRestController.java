package com.spec.api_sugflora.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.GeneroWriteDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadActiveException;
import com.spec.api_sugflora.exceptions.EntityAlreadExistsException;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;


@RestController
@RequestMapping("/api/genero")
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

    @GetMapping("familia/{id_familia}")
    public ResponseEntity<GenericResponse> getByFamiliaId(@PathVariable Integer id_familia) {

        try {
            List<Genero> generos = generoService.findAllByFamiliaId(id_familia);
            return getResponseOK(null, generos, Map.of("total_items", generos.size()));
        
        } catch (Exception e) {
            return getResponseException(e);
        }

    }

}
