package com.spec.api_sugflora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Especie;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.repository.EspecieRepository;
import com.spec.speedspring.core.exception.EntityAlreadActiveException;

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

    public Optional<Especie> findByNome(String nome) {
        return especieRepository.findByNome(nome);
    }

    public Especie save(Especie especie) {
        if (findByNome(especie.getNome()).isPresent()) {
            throw new EntityAlreadActiveException("Já existe uma espécie com este nome");
        }

        Especie saved = especieRepository.save(especie);
        return saved;
    }

    public List<Especie> findAllByGeneroId(Integer genero_id) {

        List<Especie> especies = especieRepository.findAllByGeneroId(genero_id);
        return especies;

    }

}
