package com.spec.api_sugflora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.exceptions.EntityNotFoundException;
import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.repository.ColetaRepository;

@Service
public class ColetaService {

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

}
