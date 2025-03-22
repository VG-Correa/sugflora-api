package com.spec.api_sugflora.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.repository.ProjetoRepository;

import jakarta.persistence.EntityExistsException;

@Service
public class ProjetoService {

    @Autowired
    ProjetoRepository projetoRepository;

    public boolean existByNomeAndUsuarioUuid(String nome, UUID id_usuario_dono) {
        Projeto exist = projetoRepository.findByNomeAndDonoId(nome, id_usuario_dono).orElse(null);
        return exist != null;
    }

    public Projeto update(Projeto projeto) {
        Projeto exist = projetoRepository.findByNomeAndDonoId(projeto.getNome(), projeto.getDono().getId())
                .orElse(null);

        if (exist != null && exist.getId() != projeto.getId()) {
            throw new EntityExistsException("Já existe um projeto com este nome para este usuário");
        }

        projeto.setUpdatedAt(LocalDateTime.now());

        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoSalvo;
    }

    public Projeto save(Projeto projeto) throws Exception {
        if (existByNomeAndUsuarioUuid(projeto.getNome(), projeto.getDono().getId())) {
            throw new EntityExistsException("Já existe um projeto com este nome para este usuário");
        }
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoSalvo;
    }

    public List<Projeto> findAll() {
        List<Projeto> projetos = projetoRepository.findAll();
        return projetos;
    }

    public Projeto findById(Integer id) {
        if (id == null) {
            return null;
        }

        return projetoRepository.findById(id).orElse(null);
    }

}
