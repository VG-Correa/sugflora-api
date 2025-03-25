package com.spec.api_sugflora.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
    
    public Optional<Projeto> findByNomeAndDonoId(String nome, UUID donoUuid);

    public Optional<List<Projeto>> findByDonoId(UUID id);

}
