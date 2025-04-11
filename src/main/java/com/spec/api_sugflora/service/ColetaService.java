package com.spec.api_sugflora.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.repository.ColetaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ColetaService {

    @Autowired
    private ColetaRepository coletaRepository;

    @Autowired
    private CampoService campoService;

    public List<Coleta> findAllByCampoId(Integer id_campo) {

        if (campoService.findById(id_campo).orElse(null) == null) {
            throw new EntityNotFoundException("Campo não encontrado com ID fornecido");
        }

        List<Coleta> coletas = coletaRepository.findAllByCampoId(id_campo);

        return coletas;
    }
    
}
