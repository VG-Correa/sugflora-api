package com.spec.api_sugflora.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.repository.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    public Optional<Genero> findById(Integer genero_id) {
        if (genero_id == null) {
            return null;
        }

        Optional<Genero> genero = generoRepository.findById(genero_id);

        return genero;

    }

}
