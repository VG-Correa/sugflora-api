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

import com.spec.api_sugflora.dto.ColetaDTO;
import com.spec.api_sugflora.dto.ColetaWriteDTO;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.service.CampoService;
import com.spec.api_sugflora.service.ColetaService;
import com.spec.api_sugflora.service.EspecieService;
import com.spec.api_sugflora.service.FamiliaService;
import com.spec.api_sugflora.service.GeneroService;
import com.spec.api_sugflora.service.ProjetoService;
import com.spec.api_sugflora.service.UsuarioService;
import com.spec.speedspring.core.controller.GenericRestController;
import com.spec.speedspring.core.responses.GenericResponse;

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

            Projeto projeto = projetoService.findByIdOrThrow(coletaWriteDTO.getProjeto_id());
            coleta.setProjeto(projeto);

            Campo campo = campoService.findByIdOrThrow(coletaWriteDTO.getCampo_id());
            coleta.setCampo(campo);

            Usuario responsavel = usuarioService.findByIdOrThrow(coletaWriteDTO.getResponsavel_id());
            coleta.setResponsavel(responsavel);

            coleta.setData_coleta(coletaWriteDTO.getData_coleta());

            if (coletaWriteDTO.getFamilia_id() != null) {
                Familia familia = familiaService.findByIdOrThrow(coletaWriteDTO.getFamilia_id());
                coleta.setFamilia(familia);
            }

            if (coletaWriteDTO.getGenero_id() != null) {
                Genero genero = generoService.findByIdOrThrow(coletaWriteDTO.getGenero_id());
                coleta.setGenero(genero);
            }

            if (coletaWriteDTO.getEspecie_id() != null) {
                Especie especie = especieService.findByIdOrThrow(coletaWriteDTO.getEspecie_id());
                coleta.setEspecie(especie);
            }

            coleta.setObservacao(coletaWriteDTO.getObservacao());

            Coleta coletaSaved = coletaService.save(coleta);

            return getResponseCreated("Coleta criada com sucesso", coletaSaved.toDTO());

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

    @GetMapping("campo/{id_campo}")
    public ResponseEntity<GenericResponse> getByCampo(@PathVariable Integer id_campo) {
        try {
            List<Coleta> coletas = coletaService.findAllByCampoId(id_campo);
            List<ColetaDTO> coletaDTOs = toDTO(coletas);

            return getResponseOK(null, coletaDTOs, Map.of("total_items", coletaDTOs.size()));
        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> update(@RequestBody ColetaWriteDTO coletaWriteDTO) {

        try {

            Coleta backup = coletaService.findByIdOrThrow(coletaWriteDTO.getId());
            Coleta updated = coletaService.update(coletaWriteDTO);

            return getResponseOK("Coleta atualizada com sucesso", updated.toDTO(), Map.of("backup", backup.toDTO()));

        } catch (Exception e) {
            return getResponseException(e);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable Integer id) {
        try {

            return getResponseOK(null);
            // TODO: Continuar aqui

        } catch (Exception e) {
            return getResponseException(e);
        }
    }

}
