package com.spec.api_sugflora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.repository.CampoRepository;

@Service
public class CampoService {

    @Autowired
    CampoRepository campoRepository;

    public Campo save(Campo campo) {
        Campo campoSaved = campoRepository.save(campo);
        return campoSaved;
    }

    public List<Campo> findAllByUsuarioId(UUID id_usuario) {
        List<Campo> campos = campoRepository.findAllByResponsavelId(id_usuario).get();
        return campos;
    }

}
