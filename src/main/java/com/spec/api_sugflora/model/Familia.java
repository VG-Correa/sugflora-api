package com.spec.api_sugflora.model;

import com.spec.api_sugflora.dto.FamiliaDTO;
import com.spec.api_sugflora.dto.FamiliaWriteDTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Familia extends IntDomain implements DTOConvertable<FamiliaWriteDTO, FamiliaDTO> {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @Override
    public Class<FamiliaDTO> getDTOClass() {
        return FamiliaDTO.class;
    }

}
