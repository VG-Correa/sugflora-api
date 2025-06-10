package com.spec.api_sugflora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.ColetaWriteDTO;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.repository.ColetaRepository;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ColetaService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private CampoService campoService;

    @Autowired
    FamiliaService familiaService;

    @Autowired
    GeneroService generoService;

    @Autowired
    EspecieService especieService;

    public List<Coleta> findAllByCampoId(Integer id_campo) {

        if (campoService.findById(id_campo) == null) {
            throw new EntityNotFoundException("Campo não encontrado com ID fornecido");
        }

        List<Coleta> coletas = coletaRepository.findAllByCampoId(id_campo);

        return coletas;
    }

    public Coleta save(Coleta coleta) {
        coleta.isValid();

        if (coleta.getFamilia().getId() != coleta.getGenero().getFamilia().getId()) {
            throw new EntityNotFoundException("A família da coleta não é a mesma do gênero");
        }

        if (coleta.getGenero().getId() != coleta.getEspecie().getGenero().getId()) {
            throw new EntityNotFoundException("O gênero da coleta não é o mesmo da espécie");
        }

        if (coleta.getCampo().getProjeto().getId() != coleta.getProjeto().getId()) {
            throw new EntityNotFoundException("O campo da coleta não é o mesmo do projeto");
        }

        return coletaRepository.save(coleta);
    }

    public Coleta findByIdOrThrow(Integer id) {
        return coletaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Coleta não localizada"));
    }

    @Transactional
    public Coleta update(ColetaWriteDTO coletaWriteDTO) {

        Coleta coleta = findByIdOrThrow(coletaWriteDTO.getId());

        coleta.initBy(coletaWriteDTO);

        
        if (coletaWriteDTO.getCampo_id() != coleta.getCampo().getId()) {
            throw new EntityInvalidException("Não é possível trocar o Campo da coleta");
        }

        Familia familia;
        Genero genero;
        Especie especie;

        if (coletaWriteDTO.getFamilia_id() != null) {
            familia = familiaService.findByIdOrThrow(coletaWriteDTO.getFamilia_id());
        } else {
            familia = null;
        }

        if (familia != null && coletaWriteDTO.getGenero_id() != null) {
            genero = generoService.findByIdOrThrow(coletaWriteDTO.getGenero_id());

            if (genero.getFamilia().getId() != familia.getId()) {
                throw new EntityInvalidException("Esse Gênero não pertence a essa família");
            }

        } else {
            genero = null;
        }

        if (genero != null && coletaWriteDTO.getEspecie_id() != null) {

            especie = especieService.findByIdOrThrow(coletaWriteDTO.getEspecie_id());

            if (especie.getGenero().getId() != genero.getId()) {
                throw new EntityInvalidException("Essa Espécie não pertence a esse Gênero");
            }

        } else {
            especie = null;
        }

        coleta.setFamilia(familia);
        coleta.setGenero(genero);
        coleta.setEspecie(especie);

        coleta.updateDateNow();

        return coleta;
    }

}
