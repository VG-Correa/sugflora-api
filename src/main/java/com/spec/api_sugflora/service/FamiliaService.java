package com.spec.api_sugflora.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.FamiliaWriteDTO;
import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.repository.FamiliaRepository;
import com.spec.speedspring.core.exception.EntityAlreadExistsException;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class FamiliaService {

    @Autowired
    private FamiliaRepository familiaRepository;

    public Optional<Familia> findById(Integer familia_id) {

        if (familia_id == null) {
            return null;
        }

        Optional<Familia> familia = familiaRepository.findById(familia_id);

        return familia;
    }

    public Familia findByIdOrThrow(Integer familia_id) {
        Optional<Familia> familia = findById(familia_id);
        if (familia != null && familia.isPresent()) {
            return familia.get();
        } else {
            throw new EntityNotFoundException("Familia não encontrada com o id: " + familia_id);
        }

    }

    public Familia findByNameOrThrow(String name) {
        if (name == null) {
            return null;
        }

        Optional<Familia> familia = familiaRepository.findByNome(name);

        if (familia.isPresent()) {
            return familia.get();
        } else {
            throw new EntityNotFoundException("Familia não encontrada com o nome: " + name);
        }
    }

    public Familia findByName(String name) {
        try {
            Familia familia = findByNameOrThrow(name);
            return familia;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existByName(String name) {
        try {
            if (name == null) {
                return false;
            }
            return familiaRepository.findByNome(name).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    public Familia save(Familia familia) {

        if (familia == null) {
            throw new EntityInvalidException("Familia não pode ser nula");
        }

        familia.isValid();

        if (existByName(familia.getNome())) {
            throw new EntityAlreadExistsException("Familia já existe com o nome: " + familia.getNome());
        }

        return familiaRepository.save(familia);

    }

    public List<Familia> findAll() {
        return familiaRepository.findAll();
    }

    @Transactional
    public Familia update(FamiliaWriteDTO familiaWriteDTO) {

        if (familiaWriteDTO == null || familiaWriteDTO.getId() == null) {
            throw new EntityInvalidException("Familia não pode ser nula");
        }

        Familia familia = findByIdOrThrow(familiaWriteDTO.getId());

        Familia nomeExist = findByName(familiaWriteDTO.getNome());

        if (nomeExist != null && nomeExist.getId() != familia.getId()) {
            throw new EntityAlreadExistsException("Familia já existe com o nome: " + familiaWriteDTO.getNome());
        }

        if (familiaWriteDTO.getNome() == null || familiaWriteDTO.getNome().isEmpty()) {
            throw new EntityInvalidException("Nome da familia não pode ser nulo ou vazio");
        }

        familia.setNome(familiaWriteDTO.getNome());
        familia.setDescricao(familiaWriteDTO.getDescricao());
        familia.updateDateNow();
        familia.isValid();

        return familia;
    }

    public void delete(Familia familia) {
        if (familia == null) {
            throw new EntityInvalidException("Familia não pode ser nula");
        }

        if (familia.getId() == null) {
            throw new EntityInvalidException("Familia não pode ser nula");
        }

        familiaRepository.delete(familia);
    }

}
