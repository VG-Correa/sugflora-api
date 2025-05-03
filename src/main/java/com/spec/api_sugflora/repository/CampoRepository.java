package com.spec.api_sugflora.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Campo;

@Repository
public interface CampoRepository extends JpaRepository<Campo, Integer> {

    @Query(value = "SELECT campo FROM Campo campo WHERE campo.responsavel.id = :id_usuario")
    Optional<List<Campo>> findAllByResponsavelId(UUID id_usuario);

    Optional<Campo> findByNomeAndResponsavelId(String nome, UUID responsavel_id);

    Optional<Campo> findByNomeAndResponsavelIdAndProjetoId(String nome, UUID responsavel_id, Integer projeto_id);

    @Query(value = "SELECT campo FROM Campo campo WHERE campo.projeto.id = :id_projeto")
    List<Campo> findAllByProjetoId(Integer id_projeto);

}
