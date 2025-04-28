package com.spec.api_sugflora.model;

import com.spec.api_sugflora.dto.NomePopularDTO;
import com.spec.api_sugflora.dto.NomePopularWriteDTO;
import com.spec.speedspring.core.dtoConvertable.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class NomePopular extends IntDomain implements DTOConvertable<NomePopularWriteDTO, NomePopularDTO> {

    @Column(nullable = false, unique = false)
    private String nome;

    @Override
    public Class<NomePopularDTO> getDTOClass() {
        return NomePopularDTO.class;
    }

    @Override
    public boolean getLog() {
        return false;
    }

}
