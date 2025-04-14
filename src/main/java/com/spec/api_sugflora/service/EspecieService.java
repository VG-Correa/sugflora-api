package com.spec.api_sugflora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.repository.EspecieRepository;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public Optional<Especie> findById(Integer especie_id) {
        if (especie_id == null) {
            return null;
        }

        Optional<Especie> especie = especieRepository.findById(especie_id);

        return especie;

    }

}
