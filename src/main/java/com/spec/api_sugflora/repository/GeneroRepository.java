package com.spec.api_sugflora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}
