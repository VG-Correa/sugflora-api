package com.spec.api_sugflora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spec.api_sugflora.model.Coleta;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, Integer>{

    List<Coleta> findAllByCampoId(Integer id_campo);

    
}
