package com.spec.api_sugflora.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spec.api_sugflora.dto.ColetaDTO;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.service.ColetaService;

@RestController
@RequestMapping("api/coleta")
public class ColetaRestController extends GenericRestController{

    @Autowired
    ColetaService coletaService;

    @GetMapping("campo/{id_campo}")
    public ResponseEntity<GenericResponse> getByCampo(@PathVariable Integer id_campo) {
        try {
            List<Coleta> coletas = coletaService.findAllByCampoId(id_campo);
            List<ColetaDTO> coletaDTOs = toDTO(coletas);           

            return getResponseOK(null, coletaDTOs, Map.of("total_items", coletaDTOs.size()));
        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (Exception e) {
            return getResponseInternalError(e);
        }

    }
    
}
