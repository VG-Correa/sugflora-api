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

import com.spec.api_sugflora.dto.ColetaDTO;
import com.spec.api_sugflora.dto.ColetaWriteDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadExistsException;
import com.spec.api_sugflora.exceptions.EntityAlreadyDeletedException;
import com.spec.api_sugflora.exceptions.EntityInvalidException;
import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;
import com.spec.api_sugflora.model.responses.GenericResponse;
import com.spec.api_sugflora.service.CampoService;
import com.spec.api_sugflora.service.ColetaService;
import com.spec.api_sugflora.service.EspecieService;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;

@RestController
@RequestMapping("api/coleta")
public class ColetaRestController extends GenericRestController {

    @Autowired
    ColetaService coletaService;

    @Autowired
    ProjetoService projetoService;

    @Autowired
    CampoService campoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FamiliaService familiaService;

    @Autowired
    GeneroService generoService;

    @Autowired
    EspecieService especieService;

    @PostMapping("")
    public ResponseEntity<GenericResponse> create(@RequestBody ColetaWriteDTO coletaWriteDTO) {
        try {
            Coleta coleta = new Coleta();

            Projeto projeto = projetoService.findById(coletaWriteDTO.getProjeto_id());
            coleta.setProjeto(projeto);

            Campo campo = campoService.findById(coletaWriteDTO.getCampo_id());
            coleta.setCampo(campo);

            Usuario responsavel = usuarioService.findById(coletaWriteDTO.getResponsavel_id());
            coleta.setResponsavel(responsavel);

            coleta.setData_coleta(coletaWriteDTO.getData_coleta());

            if (coletaWriteDTO.getFamilia_id() != null) {
                Familia familia = familiaService.findById(coletaWriteDTO.getFamilia_id()).get();
                coleta.setFamilia(familia);
            }

            if (coletaWriteDTO.getGenero_id() != null) {
                Genero genero = generoService.findById(coletaWriteDTO.getGenero_id()).get();
                coleta.setGenero(genero);
            }

            if (coletaWriteDTO.getEspecie_id() != null) {
                Especie especie = especieService.findById(coletaWriteDTO.getEspecie_id()).get();
                coleta.setEspecie(especie);
            }

            coleta.setObservacao(coletaWriteDTO.getObservacao());

            Coleta coletaSaved = coletaService.save(coleta);

            return getResponseOK("Coleta criada com sucesso", coletaSaved);
        } catch (EntityNotFoundException e) {
            return getResponseNotFound(e);
        } catch (EntityAlreadExistsException e) {
            return getResponseEntityExistsException(e);
        } catch (EntityInvalidException e) {
            return getResponseInvalidEntity(e);

        } catch (Exception e) {
            return getResponseInternalError(e);
        }
    }

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
