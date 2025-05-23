package com.spec.api_sugflora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.NomePopular;

@Repository
public interface NomePolularRepository extends JpaRepository<NomePopular, Integer> {

}
