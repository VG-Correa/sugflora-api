package com.spec.api_sugflora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;

public class Projeto extends IntDomain implements DTOConvertable<ProjetoDTO> {

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @Column(nullable = false, unique = false)
    private Usuario dono;

    public Projeto(){}

    public Projeto(ProjetoDTO dto) {
        this.initBy(dto);
    }

    @Override
    @JsonIgnore 
    public Class<ProjetoDTO> getDTOClass() {
       return ProjetoDTO.class;
    }
    

}
