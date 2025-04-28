package com.spec.api_sugflora.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.CampoDTO;
import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.repository.CampoRepository;
import com.spec.speedspring.core.exception.EntityAlreadExistsException;
import com.spec.speedspring.core.exception.EntityAlreadyDeletedException;
import com.spec.speedspring.core.exception.EntityInvalidException;
import com.spec.speedspring.core.exception.EntityNotFoundException;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@Service
public class CampoService {

    @Autowired
    CampoRepository campoRepository;

    public Campo findById(Integer id) {
        Campo campo = campoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campo não encontrado com ID fornecido"));
        return campo;
    }

    public Campo findByNomeAndResponsavelId(String nome, UUID responsavel_id) {
        Campo campo = campoRepository.findByNomeAndResponsavelId(nome, responsavel_id).orElse(null);
        return campo;
    }

    public Campo findByNomeAndResponsavelIdAndProjetoId(String nome, UUID responsavel_id, Integer projeto_id) {
        Campo campo = campoRepository.findByNomeAndResponsavelIdAndProjetoId(nome, responsavel_id, projeto_id)
                .orElse(null);
        return campo;
    }

    public boolean CampoAlreadExists(Campo campo) {
        return findByNomeAndResponsavelId(campo.getNome(), campo.getResponsavel().getId()) != null;
    }

    public Campo save(Campo campo) {
        if (CampoAlreadExists(campo)) {
            throw new EntityExistsException("Já existe um campo com este nome");
        }

        Campo campoSaved = campoRepository.save(campo);
        return campoSaved;
    }

    public List<Campo> findAllByUsuarioId(UUID id_usuario) {
        List<Campo> campos = campoRepository.findAllByResponsavelId(id_usuario).get();
        return campos;
    }

    public void isValid(CampoWriteDTO campoWriteDTO) {

        if (campoWriteDTO.getNome() == null) {
            throw new EntityInvalidException("O campo precisa de um nome válido");
        } else if (campoWriteDTO.getUsuario_responsavel_uuid() == null) {
            throw new EntityInvalidException("O UUID do usuário não pode ser nulo");

        } else if (campoWriteDTO.getProjeto_id() == null) {
            throw new EntityInvalidException("O id do projeto não pode ser nulo");

        } else if (campoWriteDTO.getEndereco() == null) {
            throw new EntityInvalidException("O Endereço não pode ser nulo");

        } else if (campoWriteDTO.getCidade() == null) {
            throw new EntityInvalidException("A Cidade não pode ser nula");

        } else if (campoWriteDTO.getEstado() == null) {
            throw new EntityInvalidException("O Estado não pode ser nulo");

        } else if (campoWriteDTO.getPais() == null) {
            throw new EntityInvalidException("O Pais não pode ser nulo");

        } else if (campoWriteDTO.getCep() == null) {
            throw new EntityInvalidException("O Cep não pode ser nulo");
        }

    }

    @Transactional
    public CampoDTO update(CampoWriteDTO campoWriteDTO) {

        Campo campo = findById(campoWriteDTO.getId());

        Campo campoExists = findByNomeAndResponsavelIdAndProjetoId(campoWriteDTO.getNome(),
                campoWriteDTO.getUsuario_responsavel_uuid(), campoWriteDTO.getProjeto_id());

        if (campoExists != null) {
            throw new EntityAlreadExistsException("Já existe um campo com este nome no projeto e para este usuario");
        }

        isValid(campoWriteDTO);

        CampoDTO campoBeckup = campo.toDTO();
        campo.initBy(campoWriteDTO);

        return campoBeckup;
    }

    @Transactional
    public CampoDTO delete(Integer id) {
        Campo campo = findById(id);

        if (campo.isDeleted()) {
            throw new EntityAlreadyDeletedException("Campo já está deletado");
        }

        CampoDTO beckup = campo.toDTO();

        campo.setDeleted(true);
        campo.setDeletedAt(LocalDateTime.now());
        campo.setDeletedById(null);
        campo.setUpdatedAt(LocalDateTime.now());

        return beckup;
    }

}
