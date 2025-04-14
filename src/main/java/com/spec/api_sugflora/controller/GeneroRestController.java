package com.spec.api_sugflora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.GeneroWriteDTO;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.responses.GenericResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/genero")
public class GeneroRestController extends GenericRestController {

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody GeneroWriteDTO generoWriteDTO) {
        try {

            Genero genero = new Genero(generoWriteDTO);

            return getResponseInternalError(new Exception("End-point ainda não implementado"));

        } catch (Exception e) {
            return getResponseInternalError(e);
        }
    }

}
