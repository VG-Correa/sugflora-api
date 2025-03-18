package com.spec.api_sugflora.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.repository.ProjetoRepository;

@Service
public class ProjetoService {
    
    @Autowired
    ProjetoRepository projetoRepository;

    public boolean existByNomeAndUsuarioUuid(String nome, UUID id_usuario_dono) {
       return projetoRepository.findByNomeAndDonoId(nome, id_usuario_dono) !=null;
    }

    public Projeto save(Projeto projeto) throws Exception {
        if (existByNomeAndUsuarioUuid(projeto.getNome(), projeto.getDono().getId())) {
            throw new Exception("erro");
        }
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return projetoSalvo;
    }

    public List<Projeto> findAll() {
        List<Projeto> projetos = projetoRepository.findAll();
        return projetos;
    }

}
