package com.spec.api_sugflora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Especie;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {

    Optional<Especie> findByNome(String nome);

    List<Especie> findAllByGeneroId(Integer genero_id);

}
