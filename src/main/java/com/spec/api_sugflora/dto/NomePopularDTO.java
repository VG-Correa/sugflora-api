package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.NomePopular;
import com.spec.api_sugflora.model.interfaces.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NomePopularDTO extends IntDomainDTO implements DTO<NomePopular> {

    private String nome;

    @Override
    public Class<NomePopular> getModelClass() {
        return NomePopular.class;
    }

}
