package com.spec.api_sugflora.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.EspecieDTO;
import com.spec.api_sugflora.dto.EspecieWriteDTO;
import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.service.EspecieService;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.responses.GenericResponse;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/especie")
public class EspecieRestController extends GenericRestController {

    @Autowired
    FamiliaService familiaService;

    @Autowired
    GeneroService generoService;

    @Autowired
    EspecieService especieService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody EspecieWriteDTO especieWriteDTO) {
        try {

            Especie especie = new Especie(especieWriteDTO);
            especie.setId(null);
            especie.setGenero(generoService.findByIdOrThrow(especieWriteDTO.getGenero_id()));
            especie.setFamilia(especie.getGenero().getFamilia());

            Especie saved = especieService.save(especie);

            return getResponseCreated("Espécie criada com sucesso", saved);

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> getById(@PathVariable Integer id) {
        try {
            Especie especie = especieService.findByIdOrThrow(id);
            return getResponseOK(null, especie.toDTO());
        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("genero/{genero_id}")
    public ResponseEntity<GenericResponse> getAllByGeneroId(@PathVariable Integer genero_id) {
        try {

            List<Especie> especies = especieService.findAllByGeneroId(genero_id);
            return getResponseOK(null, toDTO(especies), Map.of("total_items", especies.size()));

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody EspecieWriteDTO especieWriteDTO) {

        try {
            EspecieDTO backup = especieService.findByIdOrThrow(especieWriteDTO.getId()).toDTO();
            Especie updated = especieService.update(especieWriteDTO);

            return getResponseOK("Espécie Atualizada com sucesso", updated.toDTO(), Map.of("backup", backup));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {
        try {
            EspecieDTO backup = especieService.deleteById(id);
            return getResponseDeleted("Espécie deletada com sucesso", backup);
        } catch (Exception e) {
            return getResponseException(e);
        }
    }

}


        

    