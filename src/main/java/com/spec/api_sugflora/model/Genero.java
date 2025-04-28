package com.spec.api_sugflora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.FamiliaDTO;
import com.spec.api_sugflora.dto.GeneroDTO;
import com.spec.api_sugflora.dto.GeneroWriteDTO;
import com.spec.speedspring.core.dtoConvertable.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Genero extends IntDomain implements DTOConvertable<GeneroWriteDTO, GeneroDTO> {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "familia_id", nullable = false)
    @JsonIgnore
    private Familia familia;

    public Genero(GeneroWriteDTO generoWriteDTO) {
        initBy(generoWriteDTO);
    }

    @Override
    public Class<GeneroDTO> getDTOClass() {
        return GeneroDTO.class;
    }

    @Override
    public boolean getLog() {
        return false;
    }

}
