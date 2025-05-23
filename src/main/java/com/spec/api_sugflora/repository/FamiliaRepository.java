package com.spec.api_sugflora.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Familia;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Integer> {

    Optional<Familia> findByNome(String name);

}
