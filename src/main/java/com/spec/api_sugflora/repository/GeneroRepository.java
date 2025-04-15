package com.spec.api_sugflora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

    Optional<Genero> findByNome(String nome);

    List<Genero> findByFamiliaId(Integer id_familia);

}
