package com.spec.api_sugflora.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.exceptions.EntityAlreadActiveException;
import com.spec.api_sugflora.exceptions.EntityAlreadyDeletedException;
import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.repository.ProjetoRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjetoService {

    @Autowired
    ProjetoRepository projetoRepository;

    @Autowired
    ComunsService comunsService;

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

        Projeto projeto = projetoRepository.findById(id).orElse(null);

        if (projeto == null) {
            throw new EntityNotFoundException("Projeto não localizado");
        }

        return projeto;
    }

    public List<Projeto> findAllByUserId(UUID id_usuario) {

        if (!comunsService.UUID_USUARIO_EXISTS(id_usuario)) {
            throw new EntityExistsException("Usuario não encontrado");
        }

        List<Projeto> projetos = projetoRepository.findByDonoId(id_usuario).get();
        return projetos;

    }

    public ProjetoDTO forceDeleteById(Integer id) {
        Projeto projeto = findById(id);
        if (projeto == null) {
            throw new EntityNotFoundException("Projeto não localizado");
        }

        ProjetoDTO beckup = projeto.toDTO();

        projetoRepository.delete(projeto);
        return beckup;
    }

    @Transactional
    public Projeto deleteById(Integer id) {

        Projeto projeto = findById(id);

        if (projeto.isDeleted()) {
            throw new EntityAlreadyDeletedException("Projeto já está deletado");
        }

        projeto.setDeleted(true);
        projeto.setDeletedAt(LocalDateTime.now());
        projeto.setDeletedById(null);
        projeto.setUpdatedAt(LocalDateTime.now());
        projeto.setUpdatedById(null);

        return projeto;
    }

    @Transactional
    public void reactiveBYId(Integer id) {
        Projeto projeto = findById(id);

        if (!projeto.isDeleted()) {
            throw new EntityAlreadActiveException("Projeto já esta ativo");
        }

        projeto.setDeleted(false);
        projeto.setDeletedAt(null);
        projeto.setDeletedById(null);
        projeto.setUpdatedAt(LocalDateTime.now());
        projeto.setUpdatedById(null);
    }

    @Transactional
    public void updateImagem(MultipartFile imagem, Integer id) throws IOException {
        
        Projeto projeto = findById(id);
        projeto.setImagem(imagem.getBytes());
        
    }

}
