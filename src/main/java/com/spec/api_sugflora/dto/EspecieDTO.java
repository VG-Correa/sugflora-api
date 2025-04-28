package com.spec.api_sugflora.dto;

import java.util.List;

import com.spec.api_sugflora.model.Especie;
import com.spec.speedspring.core.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class EspecieDTO extends IntDomainDTO implements DTO<Especie> {

    private Integer id;
    private String nome;
    private String descricao;
    private GeneroDTO genero;
    private List<NomePopularDTO> nomePopulares;

    @Override
    public Class<Especie> getModelClass() {
        return Especie.class;
    }

}
