package com.spec.api_sugflora.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.GeneroWriteDTO;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.Genero;
import com.spec.api_sugflora.repository.GeneroRepository;
import com.spec.speedspring.core.exception.EntityAlreadExistsException;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;

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

    public Genero findByIdOrThrow(Integer genero_id) {
        Genero genero = findById(genero_id).orElseThrow(() -> new EntityNotFoundException("Genero não encontrado"));
        return genero;
    }

    @Transactional
    public Genero update(GeneroWriteDTO generoWriteDTO) {
        Genero genero = findByIdOrThrow(generoWriteDTO.getId());

        if (genero.getFamilia().getId() != generoWriteDTO.getFamilia_id()) {
            throw new EntityInvalidException("Não é possivel mudar a família de um gênero");
        }

        genero.initBy(generoWriteDTO);
        genero.updateDateNow();

        return genero;
    }

    @Transactional
    public Genero deleteById(Integer id) {

        Genero genero = findByIdOrThrow(id);
        genero.setDeleted(true);
        genero.setDeletedAt(LocalDateTime.now());
        genero.setDeletedById(null);

        genero.updateDateNow();
        return genero;
    }

    public void reactiveById(Integer id) {
        Genero genero = findByIdOrThrow(id);
        genero.setDeleted(false);
        genero.setDeletedAt(null);
        genero.setDeletedById(null);

        genero.updateDateNow();
    }

}
