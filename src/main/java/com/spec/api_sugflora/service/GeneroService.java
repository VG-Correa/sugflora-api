package com.spec.api_sugflora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.repository.GeneroRepository;
import com.spec.speedspring.core.exception.EntityAlreadExistsException;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    FamiliaService familiaService;

    public Optional<Genero> findById(Integer genero_id) {
        if (genero_id == null) {
            return null;
        }

        Optional<Genero> genero = generoRepository.findById(genero_id);

        return genero;

    }

    public boolean existByName(String nome) {

        Genero genero = generoRepository.findByNome(nome).orElse(null);
        return genero != null;

    }

    public Genero save(Genero genero) {

        if (existByName(genero.getNome())) {
            throw new EntityAlreadExistsException("Já existe um genero com este nome");
        }

        return generoRepository.save(genero);
    }

    public List<Genero> findAllByFamiliaId(Integer id_familia) {
        familiaService.findByIdOrThrow(id_familia);

        List<Genero> generos = generoRepository.findByFamiliaId(id_familia);
        return generos;
    }

}
